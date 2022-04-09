package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.ItemDTO;
import com.kowalczyk.iwill.service.ItemService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ItemDTOMapper.mapItemToDTOList;

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

//    @PostMapping("/i/{id}")
//    public ClientServ addClientServices(@RequestBody ClientServDTO clientServDTO) {
//        return service.addClientService(mapToClientServ(clientServDTO));
//    }
//
//    @PutMapping("/cs/{id}")
//    public void updateClientService(@PathVariable Long id, @RequestBody ClientServDTO clientServDTO) {
//        service.updateClientService(mapToClientServ(clientServDTO));
//    }
}