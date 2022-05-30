package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repository.ClientServRepository;
import com.kowalczyk.iwill.repository.ItemRepository;
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
public class ClientServController {

    @Autowired
    private ClientServRepository clientServRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private ItemRepository itemRepository;


    @GetMapping("/clientservs")
    public String listClientServs(Model model) {
        List<ClientServ> listClientServs = clientServRepository.findAll();

        model.addAttribute("listClientServs", listClientServs);
        return "clientservs";
    }

    @GetMapping("/clientservs/new")
    public String showClientServNewForm(Model model) {
        List<Visit> listVisits = visitRepository.findAll();
        List<Item> listItems = itemRepository.findAll();
        model.addAttribute("clientserv", new ClientServ());
        model.addAttribute("listVisit", listVisits);
        model.addAttribute("listItems", listItems);
        return "clientserv_form";
    }

    @GetMapping("/clientservs/new/{id}")
    public String showClientServNewFormForVisit(@PathVariable("id") Integer id, Model model) {
        Visit visit = visitRepository.findById(id).get();
        List<Visit> listVisits = visitRepository.findAll();
        List<Item> listItems = itemRepository.findAll();
        model.addAttribute("clientserv", new ClientServ());
        model.addAttribute("listVisit", listVisits);
        model.addAttribute("listItems", listItems);
        model.addAttribute("visit", visit);
        return "clientserv_form";
    }

    @PostMapping(value = "/clientservs/save", params = "submit")
    public String saveClientServ(ClientServ clientServ, HttpServletRequest request, Model model){
        addCommentToClientServ(clientServ, request);
        clientServRepository.save(clientServ);
        Visit visit = clientServ.getVisit();
        Set<ClientServ> clientServSet = visit.getClientServSet();
        model.addAttribute("visit", visit);
        model.addAttribute("clientServSet", clientServSet);
        return "visit_form";
    }
    @PostMapping(value = "/clientservs/save", params = "addItem")
    public String addItemToClientServ(ClientServ clientServ, HttpServletRequest request, Model model){
        List<Item> listItems = itemRepository.findAll();
        model.addAttribute("listItems", listItems);
        model.addAttribute("clientServDesc", clientServ.getDesc());

        return "itemsss";
    }

    private void addCommentToClientServ(ClientServ clientServ, HttpServletRequest request) {
        String[] names = request.getParameterValues("name");
        String[] values = request.getParameterValues("value");

        Item item = clientServ.getComment().getItem();
//        clientServ.getVisit().getId()

        for (int i = 0; i < names.length; i++) {
            clientServ.addComment(names[i], values[i], item);
        }
    }

    @GetMapping("/clientservs/edit/{id}")
    public String showFinishingForm(@PathVariable("id") Integer id, Model model) {
        ClientServ clientserv = clientServRepository.findById(id).get();
        model.addAttribute("clientserv", clientserv);

        List<Item> listItems = itemRepository.findAll();
        model.addAttribute("listItems", listItems);

        List<Visit> listVisits = visitRepository.findAll();
        model.addAttribute("listVisit", listVisits);
        return "clientserv_form";

    }
    @GetMapping("/clientservs/finishing/{id}")
    public String showClientServEditForm(@PathVariable("id") Integer id, Model model) {
        ClientServ clientserv = clientServRepository.findById(id).get();
        model.addAttribute("clientserv", clientserv);

        List<Item> listItems = itemRepository.findAll();
        model.addAttribute("listItems", listItems);

        List<Visit> listVisits = visitRepository.findAll();
        model.addAttribute("listVisit", listVisits);
        return "clientserv_form";

    }

    @GetMapping("/clientservs/delete/{id}")
    public String showClientServDeleteForm(@PathVariable("id") Integer id, Model model) {
        clientServRepository.deleteById(id);
        return "redirect:/clientservs";

    }

}
