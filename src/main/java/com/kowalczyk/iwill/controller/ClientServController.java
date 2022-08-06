package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.ConstanceNr;
import com.kowalczyk.iwill.model.ServiceType;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repository.ClientRepository;
import com.kowalczyk.iwill.repository.ClientServRepository;
import com.kowalczyk.iwill.repository.ServiceTypeRepository;
import com.kowalczyk.iwill.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.kowalczyk.iwill.controller.VisitController.addAttributeForVisitForm;

@Controller
public class ClientServController {

    @Autowired
    private ClientServRepository clientServRepository;

    @Autowired
    private VisitRepository visitRepository;


    @Autowired
    private ServiceTypeRepository serviceTypeRepository;

    @Autowired
    private ClientRepository clientRepository;


    @GetMapping("/clientservs")
    public String listClientServs(Model model) {
        List<ClientServ> listClientServs = clientServRepository.findAll();

        model.addAttribute("listClientServs", listClientServs);
        return "clientservs";
    }

    @GetMapping(value = "/cs/new/{idServiceType}/{idVisit}")
    public String newCS(@PathVariable("idServiceType") Integer idServiceType, @PathVariable("idVisit") Integer idVisit, Model model) {
        Visit visit;
        ServiceType serviceType;
        ClientServ clientServ;
        if (idVisit != null && idVisit > 0) {
            visit = visitRepository.getById(idVisit);
            model.addAttribute("visit", visit);
        }
        if (idServiceType != null && idServiceType > 0) {
            serviceType = serviceTypeRepository.getById(idServiceType);
            clientServ = new ClientServ(serviceType);
            copyDataFromItemToClientServ(serviceType, clientServ);
            addAttributeForClientServForm(model, serviceType, clientServ);
        }
        return "cs_form";
    }

    private void addAttributeForClientServForm(Model model, ServiceType serviceType, ClientServ clientServ) {
        model.addAttribute("serviceType", serviceType);
        model.addAttribute("clientServ", clientServ);
    }

    private void copyDataFromItemToClientServ(ServiceType item, ClientServ clientServ) {
        clientServ.setPrice(item.getValue());
        clientServ.setDesc(item.getDesc());
        clientServ.setTitle(item.getName());
        clientServ.setFinalPrice(clientServ.getFinalPriceIncludingPromotion());
    }

    @GetMapping("/clientservs/delete/{id}")
    public String showClientServDeleteForm(@PathVariable("id") Integer id, Model model) {
        Visit visit = clientServRepository.getById(id).getVisit();
        clientServRepository.deleteById(id);

        addAttributeForVisitForm(model, visit, visit.getClientServSet());


        return "visit_form";
    }

    @PostMapping(value = "/cs/save")
    public String saveCS(ClientServ clientServ, HttpServletRequest request, Model model) {
        int visitId = Integer.parseInt(request.getParameter("visitId"));
        int serviceTypeId = Integer.parseInt(request.getParameter("serviceTypeId"));
        Visit visit = visitRepository.getById(visitId);
        clientServ.setServiceType(serviceTypeRepository.getById(serviceTypeId));
        clientServ.setVisit(visit);
        clientServ.setFinalPrice(clientServ.getFinalPriceIncludingPromotion());
        clientServRepository.save(clientServ);
        visit.getClientServSet().add(clientServ);
        visitRepository.save(visit);
        addAttributeForVisitForm(model, visit, visit.getClientServSet());
        return "visit_form";
    }

    @PostMapping(value = "/cs/save", params = "back")
    public String saveCS(HttpServletRequest request, Model model) {
        Visit visit = visitRepository.getById(Integer.parseInt(request.getParameter("idVisit")));
        addAttributeForVisitForm(model, visit, visit.getClientServSet());
        return "visit_form";
    }

    @GetMapping(value = "/changeToVisit/{id}")
    public String changeToVisit(@PathVariable("id") Integer id, Model model) {
        Visit visit = visitRepository.getById(id);
//        Status status = statusRepository.getById(ConstanceNr.STATUS_VISIT);
//        visit.setStatus(status);
        addAttributeForVisitForm(model, visit, visit.getClientServSet());
        return "visit_form";
    }

    @GetMapping(value = "/cs/edit/{id}")
    public String editCS(@PathVariable("id") Integer id, Model model) {
        ClientServ clientServ = clientServRepository.getById(id);
        model.addAttribute("clientServ", clientServ);
        List<ServiceType> allActiveServiceTypeList = serviceTypeRepository.findAllActive();
        addExistingServiceTypeToActiveList(clientServ, allActiveServiceTypeList);
        model.addAttribute("serviceTypeList", allActiveServiceTypeList);
        return "cs_edit_form";
    }

    @GetMapping(value = "/cs/delete/{id}")
    public String deleteCS(@PathVariable("id") Integer id, Model model) {
        ClientServ clientServ = clientServRepository.getById(id);
        model.addAttribute("clientServ", clientServ);
        List<ServiceType> allActiveServiceTypeList = serviceTypeRepository.findAllActive();
        addExistingServiceTypeToActiveList(clientServ, allActiveServiceTypeList);
        model.addAttribute("serviceTypeList", allActiveServiceTypeList);
        model.addAttribute("isDeleteAction", "true");
        return "cs_edit_form";
    }

    private void addExistingServiceTypeToActiveList(ClientServ clientServ, List<ServiceType> allActiveServiceTypeList) {
        ServiceType serviceType = clientServ != null ? clientServ.getServiceType() : null;
        if (serviceType != null && serviceType.getStatus() != null && serviceType.getStatus().getId() != ConstanceNr.STATUS_SERVICE_TYPE) {
            allActiveServiceTypeList.add(serviceType);
        }
    }

    @PostMapping(value = "/cs/save", params = "update")
    public String updateCS(ClientServ clientServ, Model model, HttpServletRequest request) {
        ServiceType selectedItem = clientServ.getServiceType();
        clientServ = updateCSByRequest(request);
        clientServ.setServiceType(selectedItem);
        clientServ.setFinalPrice(clientServ.getFinalPriceIncludingPromotion());
        clientServRepository.save(clientServ);
        addAttributeForVisitForm(model, clientServ.getVisit(), clientServ.getVisit().getClientServSet());
        return "visit_form";
    }

    @PostMapping(value = "/cs/save", params = "delete")
    public String deleteCS(ClientServ clientServ, Model model) {
        Visit visit = clientServRepository.getById(clientServ.getId()).getVisit();
        clientServRepository.deleteById(clientServ.getId());
        addAttributeForVisitForm(model, visit, visit.getClientServSet());
        return "visit_form";
    }

    @PostMapping(value = "/cs/save", params = "return")
    public String returnToVisitForm(ClientServ clientServ, Model model) {
        Visit visit = clientServRepository.getById(clientServ.getId()).getVisit();
        addAttributeForVisitForm(model, visit, visit.getClientServSet());
        return "visit_form";
    }

    @PostMapping(value = "/cs/save", params = "addPromotionToCS")
    public String refreshCSScreen(ClientServ clientServ, Model model, HttpServletRequest request) {
        int visitId = Integer.parseInt(request.getParameter("visitId"));
        int serviceTypeId = Integer.parseInt(request.getParameter("serviceTypeId"));
        Visit visit = visitRepository.getById(visitId);
        ServiceType serviceType = serviceTypeRepository.getById(serviceTypeId);
        updatePromotion(clientServ);
        addAttributeForClientServForm(model, serviceType, clientServ);
        model.addAttribute("visit", visit);

        return "cs_form";
    }

    private void updatePromotion(ClientServ source) {
        if (source.getPromotion() <= ConstanceNr.PROMOTION_MIN_VALUE) {
            source.setPromotion(ConstanceNr.PROMOTION_MIN_VALUE);
        } else if (source.getPromotion() >= ConstanceNr.PROMOTION_MAX_VALUE) {
            source.setPromotion(ConstanceNr.PROMOTION_MAX_VALUE);
        }
    }

    private void updatePromotion(ClientServ source, ClientServ destiny) {
        if (source.getPromotion() <= ConstanceNr.PROMOTION_MIN_VALUE) {
            destiny.setPromotion(ConstanceNr.PROMOTION_MIN_VALUE);
        } else if (source.getPromotion() >= ConstanceNr.PROMOTION_MAX_VALUE) {
            destiny.setPromotion(ConstanceNr.PROMOTION_MAX_VALUE);
        } else {
            destiny.setPromotion(source.getPromotion());
        }
    }

    private ClientServ updateCSByRequest(HttpServletRequest request) {
        ClientServ clientServ;
        int id = Integer.parseInt(request.getParameter("id"));
        clientServ = clientServRepository.getById(id);
        clientServ.setTitle(request.getParameter("title"));
        clientServ.setDesc(request.getParameter("desc"));
        clientServ.setPrice(Double.parseDouble(request.getParameter("price")));
        clientServ.setPromotion(Integer.parseInt(request.getParameter("promotion")));
        return clientServ;
    }
}
