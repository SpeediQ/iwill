package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.ServiceType;
import com.kowalczyk.iwill.model.Visit;
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


    @GetMapping("/clientservs")
    public String listClientServs(Model model) {
        List<ClientServ> listClientServs = clientServRepository.findAll();

        model.addAttribute("listClientServs", listClientServs);
        return "clientservs";
    }

    /*
Screen "Create New Client Service"
set fields: title, desc, item, price
button "go to Visit Screen" -> cs/save
* */
    @GetMapping(value = "/cs/new/{idserviceType}/{idVisit}")
    public String newCS(@PathVariable("idserviceType") Integer idserviceType, @PathVariable("idVisit") Integer idVisit, Model model) {
        Visit visit;
        ServiceType serviceType;
        ClientServ clientServ;
        if (idVisit != null && idVisit > 0) {
            visit = visitRepository.getById(idVisit);
            model.addAttribute("visit", visit);
        }
        if (idserviceType != null && idserviceType > 0) {
            serviceType = serviceTypeRepository.getById(idserviceType);
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
    }

    @GetMapping("/clientservs/delete/{id}")
    public String showClientServDeleteForm(@PathVariable("id") Integer id, Model model) {
        clientServRepository.deleteById(id);
        return "redirect:/clientservs";

    }

    @PostMapping(value = "/cs/save")
    public String saveCS(ClientServ clientServ, HttpServletRequest request, Model model) {
        int visitId = Integer.parseInt(request.getParameter("visitId"));
        int serviceTypeId = Integer.parseInt(request.getParameter("serviceTypeId"));
        Visit visit = visitRepository.getById(visitId);
        clientServ.setServiceType(serviceTypeRepository.getById(serviceTypeId));
        clientServ.setVisit(visit);
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

    @GetMapping(value = "/cs/edit/{id}")
    public String editCS(@PathVariable("id") Integer id, Model model) {
        ClientServ clientServ = clientServRepository.getById(id);
        model.addAttribute("clientServ", clientServ);
        model.addAttribute("items", serviceTypeRepository.findAll());
        return "cs_edit_form";
    }

    @PostMapping(value = "/cs/save", params = "update")
    public String updateCS(ClientServ clientServ, Model model, HttpServletRequest request) {
        ServiceType selectedItem = clientServ.getServiceType();
        clientServ = updateCSByRequest(request);
        clientServ.setServiceType(selectedItem);
        clientServRepository.save(clientServ);
        addAttributeForVisitForm(model, clientServ.getVisit(), clientServ.getVisit().getClientServSet());
        return "visit_form";

    }

    private ClientServ updateCSByRequest(HttpServletRequest request) {
        ClientServ clientServ;
        int id = Integer.parseInt(request.getParameter("id"));
        clientServ = clientServRepository.getById(id);
        clientServ.setTitle(request.getParameter("title"));
        clientServ.setDesc(request.getParameter("desc"));
        clientServ.setPrice(Double.parseDouble(request.getParameter("price")));
        return clientServ;
    }
}
