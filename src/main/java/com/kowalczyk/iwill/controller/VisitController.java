package com.kowalczyk.iwill.controller;



import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repository.ClientServRepository;
import com.kowalczyk.iwill.repository.ItemRepository;
import com.kowalczyk.iwill.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
public class VisitController {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/visits")
    public String listVisits(Model model){
        List<Visit> listVisits = visitRepository.findAll();
        model.addAttribute("listVisits", listVisits);
        return "visits";
    }

    @GetMapping("/visits/new")
    public String showVisitNewForm(Model model){
        model.addAttribute("visit", new Visit());
        return "visit_form";
    }

    @PostMapping(value = "/visits/save", params = "add")
    public String saveVisit(Visit visit, Model model){
        addNewClientServToVisit(visit, model);
        visitRepository.save(visit);
        int idVisit = visit.getId();
        model.addAttribute("idVisit", idVisit);
        model.addAttribute("item", new Item());
//        model.addAttribute("idd", "idd");


        return "itemsss";
    }
    @PostMapping(value = "/visits/save", params = "submit")
    public String addVisit(Visit visit, Model model){
        addNewClientServToVisit(visit, model);
        return "visits";
    }

    private void addNewClientServToVisit(Visit visit, Model model) {
        List<Item> listItems = itemRepository.findAll();
//        ClientServ newClientServ = new ClientServ(visit);
//        model.addAttribute("clientserv", newClientServ);
        model.addAttribute("listItems", listItems);

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
