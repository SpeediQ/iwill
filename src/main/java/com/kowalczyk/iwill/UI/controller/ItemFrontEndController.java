package com.kowalczyk.iwill.UI.controller;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ItemFrontEndController {

    ItemService itemService;

    public ItemFrontEndController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/registerItem")
    public String showForm(Model model) {
        Item item = new Item();
        model.addAttribute("item", item);

        return "register_item_form";
    }

//    @PostMapping("/register")
//    public String submitForm(@ModelAttribute("client") Client client) {
//        ClientCard clientCard = new ClientCard();
//        client.setClientCard(clientCard);
//        clientCardService.addClientCard(clientCard);
//        clientService.addClient(client);
//        return "register_item_success";
//    }

}
