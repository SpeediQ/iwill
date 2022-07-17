package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.ConstanceNr;
import com.kowalczyk.iwill.model.ServiceType;
import com.kowalczyk.iwill.model.Visit;
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

@Controller
public class ServiceTypeController {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private VisitRepository visitRepository;
    private StatusRepository statusRepository;

    @GetMapping("/items")
    public String listItems(Model model) {
        List<ServiceType> serviceTypeList = serviceTypeRepository.findAllActive();
        model.addAttribute("listItems", serviceTypeList);
        return "serviceType";
    }

    @GetMapping("/items/new")
    public String showItemNewForm(Model model) {
        model.addAttribute("item", new ServiceType());
        return "serviceType_form";
    }

//    @PostMapping("/items/save")
//    public String saveItem(ServiceType serviceType) {
//        serviceTypeRepository.save(serviceType);
//        return "redirect:/items";
//    }

    @GetMapping("/items/edit/{id}")
    public String showItemEditForm(@PathVariable("id") Integer id, Model model) {
        ServiceType serviceType = serviceTypeRepository.findById(id).get();
        model.addAttribute("item", serviceType);
        return "serviceType_form";

    }

    @PostMapping("/serviceType/add")
    public String addItemByVisitFlow(ServiceType serviceType, Model model, HttpServletRequest request) {
        serviceType.setStatus(statusRepository.getById(ConstanceNr.STATUS_SERVICE_TYPE));
        serviceTypeRepository.save(serviceType);
        Visit visit = visitRepository.getById(Integer.parseInt(request.getParameter("idVisit")));
        addAttributeForChooseOrCreateServiceTypeForm(model, visit);
        return "choose_or_create_serviceType_form";
    }

    @GetMapping("/serviceTypeManager")
    public String showServiceTypeManager(Model model) {
        addAttributeForServiceTypeManager(model);
        return "serviceType_manager_form";
    }

    @GetMapping("/serviceTypeManager/{id}")
    public String showSelectedServiceTypeManager(@PathVariable("id") Integer id, Model model) {
        ServiceType serviceType = serviceTypeRepository.getById(id);
        addAttributeForSelectedServiceTypeManagerForm(model, serviceType);
        return "selected_serviceType_manager_form";
    }

    private void addAttributeForSelectedServiceTypeManagerForm(Model model, ServiceType serviceType) {
        model.addAttribute("serviceType", serviceType);
    }

    @PostMapping(value = "/serviceTypeManager", params = "add")
    public String showAddAndShowServiceTypeManager(ServiceType serviceType, Model model) {
        serviceType.setStatus(statusRepository.getById(ConstanceNr.STATUS_SERVICE_TYPE));
        serviceTypeRepository.save(serviceType);
        addAttributeForServiceTypeManager(model);
        return "serviceType_manager_form";
    }

    private void addAttributeForServiceTypeManager(Model model) {
        model.addAttribute("serviceTypeSet", serviceTypeRepository.findAllActive());
        model.addAttribute("serviceType", new ServiceType());
    }


    private void addAttributeForChooseOrCreateServiceTypeForm(Model model, Visit visit) {
        model.addAttribute("serviceTypeSet", serviceTypeRepository.findAllActive());
        model.addAttribute("visit", visit);
        model.addAttribute("serviceType", new ServiceType());
    }


}
