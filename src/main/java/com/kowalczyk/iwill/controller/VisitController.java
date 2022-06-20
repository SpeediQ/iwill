package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repository.ClientCardRepository;
import com.kowalczyk.iwill.repository.ClientRepository;
import com.kowalczyk.iwill.repository.ItemRepository;
import com.kowalczyk.iwill.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Set;

@Controller
public class VisitController {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientCardRepository clientCardRepository;

    @GetMapping("/visits")
    public String listVisits(Model model) {
        List<Visit> listVisits = visitRepository.findAll();
        model.addAttribute("listVisits", listVisits);
        return "visits";
    }

    @GetMapping("/visits/new")
    public String showVisitNewForm(Model model) {
        model.addAttribute("visit", new Visit());
        return "visit_form";
    }

    @PostMapping(value = "/visits/save", params = "add")
    public String saveVisit(Visit visit, Model model) {
        visitRepository.save(visit);
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("idVisit", visit.getId());
        model.addAttribute("item", new Item());


        return "itemsss";
    }

    @PostMapping(value = "/visits/save", params = "submit")
    public String addVisit(Visit visit, Model model) {
        model.addAttribute("listVisits", visitRepository.findAll());
        visitRepository.save(visit);
        return "visits";
    }

    @GetMapping("/visits/edit/{id}")
    public String showClientServEditForm(@PathVariable("id") Integer id, Model model) {
        Visit visit = visitRepository.findById(id).get();
        Set<ClientServ> clientServSet = visit.getClientServSet();

        model.addAttribute("visit", visit);
        model.addAttribute("clientServSet", clientServSet);
        return "visit_form";

    }


}
