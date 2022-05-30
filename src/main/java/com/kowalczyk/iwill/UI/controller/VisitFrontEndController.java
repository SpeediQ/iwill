package com.kowalczyk.iwill.UI.controller;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repesitory.ClientCardRepository;
import com.kowalczyk.iwill.repesitory.ClientRepository;
import com.kowalczyk.iwill.repesitory.VisitRepository;
import com.kowalczyk.iwill.service.ClientCardService;
import com.kowalczyk.iwill.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class VisitFrontEndController {

    VisitRepository visitRepository;
    ClientCardRepository clientCardRepository;

    public VisitFrontEndController(VisitRepository visitRepository, ClientCardRepository clientCardRepository) {
        this.visitRepository = visitRepository;
        this.clientCardRepository = clientCardRepository;
    }

    @GetMapping("/registerVisit")
    public String showForm(Model model) {

        Visit visit = new Visit();
        model.addAttribute("visit", visit);

        List<ClientCard> clientCards = clientCardRepository.findAll();
        model.addAttribute("clientCards", clientCards);
        return "register_visit_form";
    }

    @PostMapping("/registerVisit")
    public String submitForm(@ModelAttribute("visit") Visit visit) {
//        visitRepository.save(visit);
        VisitCreator.setVisit(visit);
        return "register_visit_success";
    }

}
