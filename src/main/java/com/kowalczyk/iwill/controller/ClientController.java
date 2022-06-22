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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
        return "clients";
    }

    @PostMapping("/c/add")
    public String addItemByVisitFlow(Client client, Model model, HttpServletRequest request){
        ClientCard clientCard = new ClientCard();
        client.setClientCard(clientCard);
        clientRepository.save(client);
        model.addAttribute("client", new Client());
        model.addAttribute("clients", mapToClientDTOList(clientRepository.findAll()));

        return "clients";
    }

    @GetMapping(value = "/c/add/{idClient}")
    public String showClientCard(@PathVariable("idClient") Integer idClient, Model model) {
        model.addAttribute("idClient", idClient);

        Set<Visit> visitSet = new HashSet<>();

        if (idClient > 0){
            Client client = clientRepository.findById(idClient).get();
            if (client.getClientCard() != null) {
                visitSet = client.getClientCard().getVisitSet();
            }
        }
        model.addAttribute("visitSet", visitSet);
        return "ccard_form";
    }

    @GetMapping(value = "c/v/add")
    public String addVisitToClient( HttpServletRequest request) {
        int idClient = Integer.parseInt(request.getParameter("idClient"));
        Client client = clientRepository.findById(idClient).get();
        


        return "ccard_form";
    }


}
