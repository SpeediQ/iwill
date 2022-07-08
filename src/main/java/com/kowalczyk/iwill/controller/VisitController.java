package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.*;
import com.kowalczyk.iwill.repository.NumeratorRepository;
import com.kowalczyk.iwill.repository.ServiceTypeRepository;
import com.kowalczyk.iwill.repository.StatusRepository;
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
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private NumeratorRepository numeratorRepository;
    @Autowired
    private StatusRepository statusRepository;


    @PostMapping(value = "/visits/save", params = "addItem")
    public String saveVisit(Visit visit, Model model, HttpServletRequest request) {
        setCurrentStatus(visit);
        setNumberForClient(visit);
        visitRepository.save(visit);
        addAttributeForChooseOrCreateServiceTypeForm(model, visit);
        return "choose_or_create_serviceType_form";
    }

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
        Visit visit = visitRepository.getById(id);
        Set<ClientServ> clientServSet = visit.getClientServSet();
        addAttributeForVisitForm(model, visit, clientServSet);
        return "visit_form";

    }

    static public void addAttributeForVisitForm(Model model, Visit visit, Set<ClientServ> clientServSet) {
        model.addAttribute("visit", visit);
        model.addAttribute("clientServSet", clientServSet);
    }

    private void setNumberForClient(Visit visit) {
        if (visit.getCode() == null || visit.getCode().equals("")) {
            Numerator visitNumerator = numeratorRepository.getById(ConstanceNr.NUMERATOR_VISIT);
            int freeVisitNumber = visitNumerator.getValue();
            String visitCode = preparingVisitCode(visit, visitNumerator, freeVisitNumber);
            visit.setCode(visitCode);
            visitNumerator.setValue(freeVisitNumber + 1);
            numeratorRepository.save(visitNumerator);
        }
    }

    private String preparingVisitCode(Visit visit, Numerator visitNumerator, int freeVisitNumber) {
        String symbol = visitNumerator.getSymbol();
        String clientCode = visit.getClientCard().getClient().getCode();
        String visitCode = clientCode + "/" + symbol + "/" + freeVisitNumber;
        return visitCode;
    }


    private void addAttributeForChooseOrCreateServiceTypeForm(Model model, Visit visit) {
        model.addAttribute("serviceTypeSet", serviceTypeRepository.findAll());
        model.addAttribute("visit", visit);
        model.addAttribute("serviceType", new ServiceType());
    }

    private void setCurrentStatus(Visit visit) {
        Status currentStatus = statusRepository.getById(ConstanceNr.STATUS_CURRENT);
        visit.setStatus(currentStatus);
    }


}
