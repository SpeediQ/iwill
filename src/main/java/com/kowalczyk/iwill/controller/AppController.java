package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.ConstanceNr;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repository.ClientRepository;
import com.kowalczyk.iwill.repository.ClientServRepository;
import com.kowalczyk.iwill.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.kowalczyk.iwill.model.ConstanceNr.*;

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
        return getIndexForm(model);
    }

    private String getIndexForm(Model model) {
        List<Visit> visitList = visitRepository.findAll();
        int visitsCounter = visitList.size();
        int clientServCounter = clientServRepository.findAll().size();
        int clientCounter = clientRepository.findAll().size();

        double totalValue = 0;

        for (Visit visit : visitList) {
            totalValue += visit.getClientServSet().stream().mapToDouble(ClientServ::getFinalPriceIncludingPromotion).sum();
        }

        if (clientCounter != 0) {
            model.addAttribute("clientCounterString", STATISTICS_CLIENT_COUNTER + clientCounter);
        }
        if (visitsCounter != 0) {
            model.addAttribute("visitsCounterString", STATISTICS_VISIT_COUNTER + visitsCounter);
        }
        if (totalValue != 0) {
            model.addAttribute("totalValueString", STATISTICS_SUMMARY_AMOUNT + totalValue);
        }
        if (clientServCounter != 0) {
            model.addAttribute("clientServCounterString", STATISTICS_CLIENT_SERVICE_COUNTER + clientServCounter);
        }

        return "index";
    }
}
