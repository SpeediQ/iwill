package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.*;
import com.kowalczyk.iwill.repository.ClientCardRepository;
import com.kowalczyk.iwill.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientCardRepository clientCardRepository;




    @GetMapping("/c/new")
    public String showClientForm(Model model){
        model.addAttribute("client", new Client());
        model.addAttribute("clients", clientRepository.findAll());
        return "clients";
    }

    @PostMapping("/c/add")
    public String addItemByVisitFlow(Client client, Model model, HttpServletRequest request){
        ClientCard clientCard = new ClientCard();
        client.setClientCard(clientCard);
        clientRepository.save(client);
        model.addAttribute("client", new Client());
        model.addAttribute("clients", clientRepository.findAll());
//        model.addAttribute("idVisit", request.getParameter("idVisit"));

        return "clients";
    }

    @GetMapping(value = "/cc/new/{idClient}")
    public String newCS(@PathVariable("idClient") Integer idClient, Model model) {
        Client client;
        ClientCard clientCard = new ClientCard();

        if (idClient != null && idClient > 0) {
            client = clientRepository.getById(idClient);

            if (client.getClientCard() == null) {
                client.setClientCard(clientCard);
                clientRepository.save(client);
                clientCard.setClient(client);
                clientCardRepository.save(clientCard);
            } else {
                clientCard = client.getClientCard();
            }

            model.addAttribute("clientCard", clientCard);
            model.addAttribute("visitSet", clientCard.getVisitSet());
            model.addAttribute("clientCardId", clientCard.getId());
            model.addAttribute("visit", new Visit());
        }


        return "cs_form";
    }


}
