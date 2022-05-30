package com.kowalczyk.iwill.UI.controller;

import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.service.ClientServService;
import com.kowalczyk.iwill.service.CommentService;
import com.kowalczyk.iwill.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ClientServFrontEndController {

    ClientServService clientServService;
    CommentService commentService;

    public ClientServFrontEndController(ClientServService clientServService, CommentService commentService) {
        this.clientServService = clientServService;
        this.commentService = commentService;
    }

    @GetMapping("/registerClientServ")
    public String showForm(Model model) {
        ClientServ clientServ = new ClientServ();
        Visit visit = VisitCreator.getVisit();
        clientServ.setVisit(visit);
        VisitCreator.getClientServList().add(clientServ);
        visit.setClientServs(VisitCreator.getClientServList());

        model.addAttribute("clientServ", clientServ);
        model.addAttribute("visit", visit);

        return "register_client_serv_form";
    }

    @PostMapping("/registerClientServ")
    public String submitForm(@ModelAttribute("clientServ") ClientServ clientServ, Model model) {
//        clientServService.addClientService(clientServ);
        return "register_client_serv_success";
    }

}
