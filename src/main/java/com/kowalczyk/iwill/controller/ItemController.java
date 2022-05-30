package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repository.ClientServRepository;
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
import java.util.Set;

@Controller
public class ItemController {

    @Autowired
    private ItemRepository repository;

    @GetMapping("/items")
    public String listItems(Model model){
        List<Item> listItems = repository.findAll();
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
        repository.save(item);
        return "redirect:/items";
    }

    @GetMapping("/items/edit/{id}")
    public String showItemEditForm(@PathVariable("id") Integer id, Model model) {
        Item item = repository.findById(id).get();
        model.addAttribute("item", item);
        return "item_form";

    }


}
