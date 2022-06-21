package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping("/items")
    public String listItems(Model model){
        List<Item> listItems = itemRepository.findAll();
        model.addAttribute("listItems", listItems);
        return "items";
    }

    @GetMapping("/items/new")
    public String showItemNewForm(Model model){
        model.addAttribute("item", new Item());
        return "item_form";
    }

    @PostMapping("/items/save")
    public String saveItem(Item item){
        itemRepository.save(item);
        return "redirect:/items";
    }

    @GetMapping("/items/edit/{id}")
    public String showItemEditForm(@PathVariable("id") Integer id, Model model) {
        Item item = itemRepository.findById(id).get();
        model.addAttribute("item", item);
        return "item_form";

    }

    @PostMapping("/item/add")
    public String addItemByVisitFlow(Item item, Model model, HttpServletRequest request){
        itemRepository.save(item);
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("idVisit", request.getParameter("idVisit"));
        model.addAttribute("item", new Item());

        return "itemsss";
    }


}
