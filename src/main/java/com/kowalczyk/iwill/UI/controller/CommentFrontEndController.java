package com.kowalczyk.iwill.UI.controller;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.service.ClientCardService;
import com.kowalczyk.iwill.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class ClientFrontEndController {

    ClientService clientService;
    ClientCardService clientCardService;

    public ClientFrontEndController(ClientService clientService, ClientCardService clientCardService) {
        this.clientService = clientService;
        this.clientCardService = clientCardService;
    }

    @GetMapping("/registerClient")
    public String showForm(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);

        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
        model.addAttribute("listProfession", listProfession);

        return "register_client_form";
    }

    @PostMapping("/registerClient")
    public String submitForm(@ModelAttribute("client") Client client) {
        client.addDefaultClientCardToClient();
        ClientCard clientCard = client.getClientCard();
//        clientService.addClient(client);
        clientCardService.addClientCard(clientCard);
        return "register_client_success";
    }

}
