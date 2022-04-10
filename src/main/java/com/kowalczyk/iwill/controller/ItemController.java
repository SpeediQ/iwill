package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.ItemDTO;
import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.service.ItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ItemDTOMapper.mapItemToDTOList;
import static com.kowalczyk.iwill.controller.mapper.ItemMapper.mapToItem;

@RestController
public class ItemController {
    private ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping("/i")
    public List<ItemDTO> getItems() {
        return mapItemToDTOList(service.getItems());
    }

    @GetMapping("/i/{id}")
    ResponseEntity<Item> readTask(@PathVariable Long id) {
        return service.getItemById(id)
                .map(body -> ResponseEntity.ok(body))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/i/{id}")
    public Item addVisit(@RequestBody ItemDTO itemDTO) {
        return service.addItem(mapToItem(itemDTO));
    }

    @PutMapping("/i/{id}")
    public void updateVisit(@PathVariable Long id, @RequestBody ItemDTO itemDTO) {
        service.updateItem(mapToItem(itemDTO));
    }
}