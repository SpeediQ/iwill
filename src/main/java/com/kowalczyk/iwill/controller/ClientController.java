package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.ClientCardDTO;
import com.kowalczyk.iwill.controller.dto.ClientDTO;
import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.service.ClientCardService;
import com.kowalczyk.iwill.service.ClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ClientCardDTOMapper.mapClientCardToDTOList;
import static com.kowalczyk.iwill.controller.mapper.ClientCardMapper.mapToClientCard;
import static com.kowalczyk.iwill.controller.mapper.ClientDTOMapper.mapClientToDTOList;
import static com.kowalczyk.iwill.controller.mapper.ClientMapper.mapToClient;

@RestController
public class ClientController {
    private ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/c")
    public List<ClientDTO> getVisits() {
        return mapClientToDTOList(service.getClients());
    }

    @PostMapping("/c/{id}")
    public Client addVisit(@RequestBody ClientDTO clientDTO) {
        return service.addClient(mapToClient(clientDTO));
    }

    @PutMapping("/c/{id}")
    public void updateVisit(@PathVariable Long id, @RequestBody ClientDTO clientDTO) {
        service.updateClient(mapToClient(clientDTO));
    }
}