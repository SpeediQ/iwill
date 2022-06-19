package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repository.ClientServRepository;
import com.kowalczyk.iwill.repository.CommentRepository;
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
import java.util.Optional;
import java.util.Set;

@Controller
public class ClientServController {

    @Autowired
    private ClientServRepository clientServRepository;

    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ItemRepository itemRepository;


    @GetMapping("/clientservs")
    public String listClientServs(Model model) {
        List<ClientServ> listClientServs = clientServRepository.findAll();

        model.addAttribute("listClientServs", listClientServs);
        return "clientservs";
    }

    @GetMapping(value = "/cs/new/{idItem}/{idVisit}")
    public String newCS(@PathVariable("idItem") Integer idItem, @PathVariable("idVisit") Integer idVisit, Model model) {
        Visit visit;
        Item item;
        ClientServ clientServ;


        if (idVisit != null && idVisit > 0) {
            visit = visitRepository.getById(idVisit);
            model.addAttribute("visit", visit);
        }
        if (idItem != null && idItem > 0) {
            item = itemRepository.getById(idItem);
            clientServ = new ClientServ(new Comment(item));
            model.addAttribute("item", item);
            model.addAttribute("clientServ", clientServ);
        }


        return "cs_form";
    }

    @PostMapping(value = "/clientservs/save", params = "addItem")
    public String addItemToClientServ(ClientServ clientServ, HttpServletRequest request, Model model, String keyword) {
        List<Item> listItems = itemRepository.findAll();
        model.addAttribute("listItems", listItems);
        Comment comment = new Comment(clientServ);

        comment.setItem(new Item());
        model.addAttribute("comment", new Comment("dsacv"));
        model.addAttribute("item", new Item());
        model.addAttribute("idd", "idd");

        return "itemsss";
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

    @GetMapping("/clientservs/delete/{id}")
    public String showClientServDeleteForm(@PathVariable("id") Integer id, Model model) {
        clientServRepository.deleteById(id);
        return "redirect:/clientservs";

    }

    @PostMapping(value = "/cs/save")
    public String saveCS(ClientServ clientServ, HttpServletRequest request, Model model) {
        int visitId = Integer.parseInt(request.getParameter("visitId"));
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        Visit visit = visitRepository.getById(visitId);
        Comment comment = clientServ.getComment();
        comment.setItem(itemRepository.getById(itemId));
        comment.setTitle(clientServ.getTitle());
        comment.setDesc(clientServ.getDesc());
        comment.setClientServ(clientServ);
        clientServ.setVisit(visit);
        clientServRepository.save(clientServ);
        visit.getClientServSet().add(clientServ);
        Set<ClientServ> clientServSet = visit.getClientServSet();
        visitRepository.save(visit);
        model.addAttribute("visit", visit);
        model.addAttribute("clientServSet", clientServSet);

        return "visit_form";
    }

}
