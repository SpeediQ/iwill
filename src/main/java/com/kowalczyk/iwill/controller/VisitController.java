package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.*;
import com.kowalczyk.iwill.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class VisitController {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientCardRepository clientCardRepository;
    @Autowired
    private StatusRepository statusRepository;


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


    @PostMapping(value = "/visits/save", params = "addItem")
    public String saveVisit(Visit visit, Model model, HttpServletRequest request) {
        setCurrentStatus(visit);
        visitRepository.save(visit);
        model.addAttribute("serviceTypeSet", serviceTypeRepository.findAll());
        model.addAttribute("idVisit", visit.getId());
        model.addAttribute("serviceType", new ServiceType());

        return "choose_or_create_serviceType_form";
    }

    private void setCurrentStatus(Visit visit) {

    }
/*
save Visit to db
* */

    @PostMapping(value = "/visits/save", params = "submit")
    public String addVisit(Visit visit, Model model) {
        visitRepository.save(visit);
        ClientCard clientCard = visit.getClientCard();
        Client client = clientCard.getClient();
        List<Visit> visitSet = clientCard.getSortedVisitListByVisitSet();
        model.addAttribute("visitSet", visitSet);
        model.addAttribute("client", client);
        return "ccardview_form";
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
