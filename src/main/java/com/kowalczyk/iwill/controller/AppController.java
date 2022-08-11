package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repository.ClientRepository;
import com.kowalczyk.iwill.repository.ClientServRepository;
import com.kowalczyk.iwill.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AppController {

    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private ClientServRepository clientServRepository;
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("")
    public String viewHomePage(Model model) {
        List<Visit> visitList = visitRepository.findAll();
        int visitsCounter = visitList.size();
        int clientServCounter = clientServRepository.findAll().size();
        int clientCounter = clientRepository.findAll().size();

        double totalValue = 0;
        String visitsCounterString = null;
        String totalValueString = null;
        String clientServCounterString = null;
        String clientCounterString = null;

        for (Visit visit : visitList) {
            totalValue += visit.getClientServSet().stream().mapToDouble(ClientServ::getFinalPriceIncludingPromotion).sum();
        }


        if (visitsCounter != 0) {
            clientCounterString = "Łączna ilość klientów: " + clientCounter;
            visitsCounterString = "Łączna ilość wizyt: " + visitsCounter;
            clientServCounterString = "Łączna ilość usług: " + clientServCounter;
            totalValueString = "Łączna wartość: " + totalValue;
        }
        if (visitsCounter != 0) {
            model.addAttribute("clientCounterString", clientCounterString);

        }
        if (visitsCounter != 0) {
            model.addAttribute("visitsCounterString", visitsCounterString);

        }
        if (clientServCounter != 0) {
            model.addAttribute("totalValueString", totalValueString);

        }
        if (totalValue != 0) {
            model.addAttribute("clientServCounterString", clientServCounterString);

        }

        return "index";
    }
}
