package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.adapter.ClientServRepository;
import com.kowalczyk.iwill.adapter.ItemRepository;
import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/i")
    List<Item> getAll (){
        List<Item> result = repository.findAll();
        return result;
    }


    @PostMapping("/i")
    Item createClient (Item entity){
        Item body = repository.save(entity);
        return body;
    }
    @GetMapping("/i/{id}")
    public ResponseEntity<Item> getItem(@PathVariable Long id) {
        return repository.findById(id)
                .map(body -> ResponseEntity.ok(body))
                .orElse(ResponseEntity.notFound().build());
    }

    public Item copyToNewItem(Item item) {
        Item newItem = new Item();
        newItem.setDesc(item.getDesc());
        newItem.setPrice(item.getPrice());
        newItem.setTitle(item.getTitle());
        return newItem;
    }
}
