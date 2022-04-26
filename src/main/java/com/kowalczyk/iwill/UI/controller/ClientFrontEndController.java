package com.kowalczyk.iwill.UI.controller;

import com.kowalczyk.iwill.model.Client;
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

    public ClientFrontEndController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        Client client = new Client();
        model.addAttribute("client", client);

        List<String> listProfession = Arrays.asList("Developer", "Tester", "Architect");
        model.addAttribute("listProfession", listProfession);

        return "register_form";
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("client") Client client) {
        System.out.println(client);
        clientService.addClient(client);
        return "register_success";
    }

}
