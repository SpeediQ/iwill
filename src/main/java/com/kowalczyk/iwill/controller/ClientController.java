package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.*;
import com.kowalczyk.iwill.repository.ClientCardRepository;
import com.kowalczyk.iwill.repository.ClientRepository;
import com.kowalczyk.iwill.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.kowalczyk.iwill.model.mapper.ClientDTOMapper.mapToClientDTOList;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientCardRepository clientCardRepository;
    @Autowired
    private VisitRepository visitRepository;




    @GetMapping("/c/new")
    public String showClientForm(Model model){
        model.addAttribute("client", new Client());
        model.addAttribute("clients", mapToClientDTOList(clientRepository.findAll()));
        return "choose_or_create_client_form";
    }

    @PostMapping("/c/add")
    public String addItemByVisitFlow(Client client, Model model){
        ClientCard clientCard = new ClientCard();
        client.setClientCard(clientCard);
        clientCard.setClient(client);
        clientRepository.save(client);
        model.addAttribute("client", new Client());
        model.addAttribute("clients", mapToClientDTOList(clientRepository.findAll()));

        return "choose_or_create_client_form";
    }

    @GetMapping(value = "/c/add/{idClient}")
    public String showClientCard(@PathVariable("idClient") Integer idClient, Model model) {

        List<Visit> visitList = new ArrayList<>();
            Client client = clientRepository.getById(idClient);
            if (client.getClientCard() != null) {
                visitList = client.getClientCard().getSortedVisitListByVisitSet();
            }

            model.addAttribute("client", client);
            model.addAttribute("visitSet", visitList);


        return "ccard_form";
    }

    @GetMapping(value = "cc/view")
    public String showClientCard(Model model, HttpServletRequest request) {

        int idClient = Integer.parseInt(request.getParameter("idClient"));
        List<Visit> visitList = new ArrayList<>();
        Client client = clientRepository.getById(idClient);
        if (client.getClientCard() != null) {
            visitList = client.getClientCard().getSortedVisitListByVisitSet();
        }

        model.addAttribute("client", client);
        model.addAttribute("visitSet", visitList);


        return "ccard_form";
    }

    @PostMapping(value = "c/v/add")
    public String addVisitToClient(Client client, HttpServletRequest request, Model model) {

        Client newClient = clientRepository.getById(client.getId());
        ClientCard clientCard = newClient.getClientCard();
        Visit visit = new Visit();
        clientCard.getVisitSet().add(visit);
        visit.setClientCard(clientCard);
        clientCardRepository.save(clientCard);


        model.addAttribute("visit", visit);
        model.addAttribute("clientCard", clientCard);
        model.addAttribute("idClient", request.getParameter("idClient"));
        return "visit_form";
    }

}
