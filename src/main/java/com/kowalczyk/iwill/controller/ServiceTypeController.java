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
    @Autowired
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

    @GetMapping("/deleteServiceTypeManager")
    public String deleteServiceTypeManager(Model model, ServiceType serviceType) {
        serviceTypeRepository.deleteById(serviceType.getId());
        addAttributeForServiceTypeManager(model);
        return "serviceType_manager_form";
    }

    @GetMapping("/serviceTypeManager/{id}")
    public String showSelectedServiceTypeManager(@PathVariable("id") Integer id, Model model) {
        ServiceType serviceType = serviceTypeRepository.getById(id);
        addAttributeForSelectedServiceTypeManagerForm(model, serviceType);
        model.addAttribute("isEditView", true);
        return "selected_serviceType_manager_form";
    }

    private void addAttributeForSelectedServiceTypeManagerForm(Model model, ServiceType serviceType) {
        model.addAttribute("serviceType", serviceType);
        if (serviceType.getStatus() != null) {
            model.addAttribute("idStatus", serviceType.getStatus().getId());
        }
    }

    @PostMapping(value = "/addServiceTypeManager")
    public String showAddAndShowServiceTypeManager(ServiceType serviceType, Model model, HttpServletRequest request) {
        serviceType.setStatus(statusRepository.getById(ConstanceNr.STATUS_SERVICE_TYPE));
        serviceTypeRepository.save(serviceType);
        model.addAttribute("serviceType", serviceType);
        model.addAttribute("idVisit", request.getParameter("idVisit"));
        return "serviceType";
    }

    @GetMapping(value = "/saveServiceTypeManager")
    public String saveServiceTypeManager(Model model, HttpServletRequest request) {
        addAttributeForServiceTypeManager(model);
        String idVisit = request.getParameter("idVisit");
        if (idVisit == null || idVisit == "") {
            return "serviceType_manager_form";
        } else {
            Visit visit = visitRepository.getById(Integer.parseInt(idVisit));
            addAttributeForChooseOrCreateServiceTypeForm(model, visit);
            return "choose_or_create_serviceType_form";
        }
    }

    @PostMapping(value = "/saveSelectedServiceTypeManager")
    public String saveSelectedSTManager(Model model, HttpServletRequest request, ServiceType serviceType) {
        String idStatus = request.getParameter("idStatus");
        if (idStatus == null || idStatus == "") {
            return null;
        } else {
            serviceType.setStatus(statusRepository.getById(Integer.parseInt(idStatus)));
        }
        serviceTypeRepository.save(serviceType);
        model.addAttribute("serviceType", serviceType);
        model.addAttribute("isEditView", true);

        return "serviceType";
    }


    @GetMapping(value = "/cancelServiceTypeManager")
    public String candelServiceType(ServiceType serviceType, Model model, HttpServletRequest request) {
        serviceType = serviceTypeRepository.getById(serviceType.getId());
        serviceType.setStatus(statusRepository.getById(ConstanceNr.STATUS_CANCELLED));

        serviceTypeRepository.save(serviceType);
        model.addAttribute("idVisit", request.getParameter("idVisit"));
        String isEditView = request.getParameter("isEditView");
        model.addAttribute("isEditView", isEditView);
        if ("true".equals(isEditView)) {
            addAttributeForSelectedServiceTypeManagerForm(model, serviceType);
            return "selected_serviceType_manager_form";
        } else {
            model.addAttribute("serviceType", serviceType);
            return "serviceType";
        }
    }

    @GetMapping(value = "/inactiveServiceTypeManager")
    public String inactiveServiceTypeManager(ServiceType serviceType, Model model, HttpServletRequest request) {
        serviceType = serviceTypeRepository.getById(serviceType.getId());
        serviceType.setStatus(statusRepository.getById(ConstanceNr.STATUS_INACTIVE));
        serviceTypeRepository.save(serviceType);
        model.addAttribute("idVisit", request.getParameter("idVisit"));
        String isEditView = request.getParameter("isEditView");
        model.addAttribute("isEditView", isEditView);
        if ("true".equals(isEditView)) {
            addAttributeForSelectedServiceTypeManagerForm(model, serviceType);
            return "selected_serviceType_manager_form";
        } else {
            model.addAttribute("serviceType", serviceType);
            return "serviceType";
        }
    }

    @GetMapping(value = "/activeServiceTypeManager")
    public String activeServiceTypeManager(ServiceType serviceType, Model model, HttpServletRequest request) {
        serviceType = serviceTypeRepository.getById(serviceType.getId());
        serviceType.setStatus(statusRepository.getById(ConstanceNr.STATUS_SERVICE_TYPE));
        serviceTypeRepository.save(serviceType);
        model.addAttribute("idVisit", request.getParameter("idVisit"));
        String isEditView = request.getParameter("isEditView");
        model.addAttribute("isEditView", isEditView);
        if ("true".equals(isEditView)) {
            addAttributeForSelectedServiceTypeManagerForm(model, serviceType);
            return "selected_serviceType_manager_form";
        } else {
            model.addAttribute("serviceType", serviceType);
            return "serviceType";
        }
    }

    private void addAttributeForServiceTypeManager(Model model) {
        model.addAttribute("serviceTypeSet", serviceTypeRepository.findAll());
        model.addAttribute("serviceType", new ServiceType());
    }


    private void addAttributeForChooseOrCreateServiceTypeForm(Model model, Visit visit) {
        model.addAttribute("serviceTypeSet", serviceTypeRepository.findAllActive());
        model.addAttribute("visit", visit);
        model.addAttribute("serviceType", new ServiceType());
    }


}
