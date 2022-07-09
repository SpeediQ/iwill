package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.*;
import com.kowalczyk.iwill.repository.ClientCardRepository;
import com.kowalczyk.iwill.repository.ClientRepository;
import com.kowalczyk.iwill.repository.NumeratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

import static com.kowalczyk.iwill.controller.VisitController.addAttributeForVisitForm;
import static com.kowalczyk.iwill.model.mapper.ClientDTOMapper.mapToClientDTOList;

@Controller
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientCardRepository clientCardRepository;
    @Autowired
    private NumeratorRepository numeratorRepository;


    @GetMapping("/main/cform")
    public String showClientForm(Model model) {
        addAttributeForClientForm(model);
        return "choose_or_create_client_form";
    }

    @PostMapping("/main/save/client")
    public String addItemByVisitFlow(Client client, Model model) {
        ClientCard clientCard = new ClientCard();
        client.setClientCard(clientCard);
        clientCard.setClient(client);
        setNumberForClient(client);
        clientRepository.save(client);
        addAttributeForClientForm(model);
        return "choose_or_create_client_form";
    }

    private void addAttributeForClientForm(Model model) {
        model.addAttribute("client", new Client("Brak komentarza"));
        model.addAttribute("clients", mapToClientDTOList(clientRepository.findAll()));
    }

    private void setNumberForClient(Client client) {
        if (client.getCode() == null || client.getCode().equals("")) {
            Numerator clientNumerator = numeratorRepository.getById(ConstanceNr.NUMERATOR_CLIENT);
            int freeClientNumber = clientNumerator.getValue();
            String symbol = clientNumerator.getSymbol();
            String clientCode = symbol + "/" + freeClientNumber;
            client.setCode(clientCode);
            clientNumerator.setValue(freeClientNumber + 1);
            numeratorRepository.save(clientNumerator);
        }
    }


    @GetMapping(value = "/c/add/{idClient}")
    public String showClientCard(@PathVariable("idClient") Integer idClient, Model model) {
        Client client = clientRepository.getById(idClient);
        addAttributeForClientCardForm(model, client);
        return "ccard_form";
    }

    private void addAttributeForClientCardForm(Model model, Client client) {
        model.addAttribute("client", client);
    }

    @PostMapping(value = "c/v/add")
    public String addVisitToClient(Client client, HttpServletRequest request, Model model) {
        Visit visit = new Visit();
        Client newClient = clientRepository.getById(client.getId());
        ClientCard clientCard = newClient.getClientCard();
        clientCard.getVisitSet().add(visit);
        visit.setClientCard(clientCard);
        prepareDefaultVisitTitleDesc(visit);
        clientCardRepository.save(clientCard);
        addAttributeForVisitForm(model, visit, visit.getClientServSet());
        return "visit_form";
    }

    private void prepareDefaultVisitTitleDesc(Visit visit) {
        visit.setTitle("Wizyta ");
        visit.setDesc("Szczegóły dot. wizyty: ");
    }

}
