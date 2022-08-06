package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.ConstanceNr;
import com.kowalczyk.iwill.model.ServiceType;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repository.ServiceTypeRepository;
import com.kowalczyk.iwill.repository.StatusRepository;
import com.kowalczyk.iwill.repository.VisitRepository;
import com.kowalczyk.iwill.service.ServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ServiceTypeController {

    @Autowired
    private ServiceTypeRepository serviceTypeRepository;
    @Autowired
    private ServiceTypeService serviceTypeService;
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private StatusRepository statusRepository;
//    @Autowired
//    private VisitController visitController;

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

    // /page/1?sortField=name@sortDir=asc
    @GetMapping("/serviceTypeManager/p/{pageNumber}")
    public String showServiceTypeManager(Model model,
                                         @PathVariable("pageNumber") int currentPage,
                                         @RequestParam("sortField") String sortField,
                                         @RequestParam("sortDir") String sortDir
    ) {
//        Page<ServiceType> page = serviceTypeService.findAllServiceTypePage(currentPage);
        Page<ServiceType> page = serviceTypeService.findAllSortedServiceTypePage(currentPage, sortField, sortDir);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalElements", page.getTotalElements());
        model.addAttribute("serviceTypeList", page.getContent());
        model.addAttribute("serviceType", new ServiceType());
        model.addAttribute("serviceTypeSet", serviceTypeRepository.findAll());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        return "serviceType_manager_form";
    }

    @GetMapping("/serviceTypeManager/p")
    public String showMainServiceTypeManager(Model model) {
        return showServiceTypeManager(model, 1, "name", "asc");
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
        addAttributeForServiceType(serviceType, model, request);
        return "serviceType";
    }

    private void addAttributeForServiceType(ServiceType serviceType, Model model, HttpServletRequest request) {
        model.addAttribute("serviceType", serviceType);
        model.addAttribute("idVisit", request.getParameter("idVisit"));
    }

    @PostMapping(value = "/saveSelectedServiceTypeManager")
    public String saveSelectedSTManager(Model model, HttpServletRequest request, ServiceType serviceType) {
        setServiceTypeStatusByRequest(serviceType, request);
        serviceTypeRepository.save(serviceType);
        model.addAttribute("serviceType", serviceType);
        model.addAttribute("isEditView", true);
        return "serviceType";
    }

    private void setServiceTypeStatusByRequest(ServiceType serviceType, HttpServletRequest request) {
        String idStatus = request.getParameter("idStatus");
        if (idStatus == null || idStatus == "") {
            // todo
        } else {
            serviceType.setStatus(statusRepository.getById(Integer.parseInt(idStatus)));
        }
    }

    @PostMapping(value = "/saveSelectedServiceTypeManager", params = "cancelServiceTypeManager")
    public String candelServiceType(ServiceType serviceType, Model model, HttpServletRequest request) {
        serviceType = setServiceTypeStatusByServiceTypeId(serviceType, ConstanceNr.STATUS_CANCELLED);
        serviceTypeRepository.save(serviceType);
        String isEditView = request.getParameter("isEditView");
        model.addAttribute("isEditView", isEditView);
        return setCorrectView(serviceType, model, request, isEditView);
    }

    private String setCorrectView(ServiceType serviceType, Model model, HttpServletRequest request, String isEditView) {
        if ("true".equals(isEditView)) {
            addAttributeForSelectedServiceTypeManagerForm(model, serviceType);
            return "selected_serviceType_manager_form";
        } else {
            model.addAttribute("serviceType", serviceType);
            addAttributeForServiceType(serviceType, model, request);
            return "serviceType";
        }
    }

    @PostMapping(value = "/saveSelectedServiceTypeManager", params = "inactiveServiceTypeManager")
    public String inactiveServiceTypeManager(ServiceType serviceType, Model model, HttpServletRequest request) {
        serviceType = setServiceTypeStatusByServiceTypeId(serviceType, ConstanceNr.STATUS_INACTIVE);
        serviceTypeRepository.save(serviceType);
        String isEditView = request.getParameter("isEditView");
        model.addAttribute("isEditView", isEditView);
        return setCorrectView(serviceType, model, request, isEditView);
    }

    @PostMapping(value = "/saveSelectedServiceTypeManager", params = "activeServiceTypeManager")
    public String activeServiceTypeManager(ServiceType serviceType, Model model, HttpServletRequest request) {
        serviceType = setServiceTypeStatusByServiceTypeId(serviceType, ConstanceNr.STATUS_SERVICE_TYPE);
        serviceTypeRepository.save(serviceType);
        String isEditView = request.getParameter("isEditView");
        model.addAttribute("isEditView", isEditView);
        return setCorrectView(serviceType, model, request, isEditView);
    }

    private ServiceType setServiceTypeStatusByServiceTypeId(ServiceType serviceType, int statusServiceType) {
        serviceType.setStatus(statusRepository.getById(statusServiceType));
        return serviceType;
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
