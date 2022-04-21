package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.ItemDTO;
import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ItemDTOMapper.mapToItemDTO;
import static com.kowalczyk.iwill.controller.mapper.ItemDTOMapper.mapToItemDTOList;
import static com.kowalczyk.iwill.controller.mapper.ItemMapper.mapToItem;

@RestController
public class ItemController {
    private ItemService service;
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping("/i")
    public ResponseEntity<List<ItemDTO>> getItems() {
        logger.warn("Exposing all Items");
        return ResponseEntity.ok(mapToItemDTOList(service.getItems()));
    }

    @GetMapping("/i/{id}")
    ResponseEntity<ItemDTO> getItemById(@PathVariable Long id) {
        return service.getItemById(id)
                .map(body -> ResponseEntity.ok(mapToItemDTO(body)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/i/{id}")
    public ResponseEntity<Item> addItem(@RequestBody ItemDTO itemDTO) {
        Item result = service.addItem(mapToItem(itemDTO));
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

}