package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.repesitory.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> getItems() {
        return repository.findAll();
    }

    public Item addItem(Item body) {
        return repository.save(body);
    }

    public void updateItem(Item body) {
        repository.save(body);
    }


}
