package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.ClientServDTO;
import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.service.ClientServService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ClientServDTOMapper.mapClientServToDTOList;
import static com.kowalczyk.iwill.controller.mapper.ClientServMapper.mapToClientServ;

@RestController
public class ClientServController {
    private ClientServService service;

    public ClientServController(ClientServService service) {
        this.service = service;
    }

    @GetMapping("/cs")
    public List<ClientServDTO> getClientServices() {
        return mapClientServToDTOList(service.getClientServices());
    }

    @PostMapping("/cs/{id}")
    public ClientServ addClientServices(@RequestBody ClientServDTO clientServDTO) {
        return service.addClientService(mapToClientServ(clientServDTO));
    }

    @PutMapping("/cs/{id}")
    public void updateClientService(@PathVariable Long id, @RequestBody ClientServDTO clientServDTO) {
        service.updateClientService(mapToClientServ(clientServDTO));
    }
}