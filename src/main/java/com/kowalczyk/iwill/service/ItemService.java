package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.repesitory.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> getItems() {
        return repository.findAll();
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public Optional<Item> getItemById(Long id) {
        return repository.findById(id);
    }

    public Item addItem(Item body) {
        return repository.save(body);
    }

    public Item save(Item body) {
        return repository.save(body);
    }

    public void updateItem(Item body) {
        repository.save(body);
    }


}
