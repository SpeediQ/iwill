package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.ClientDTO;
import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ClientDTOMapper.mapToClientDTO;
import static com.kowalczyk.iwill.controller.mapper.ClientDTOMapper.mapToClientDTOList;
import static com.kowalczyk.iwill.controller.mapper.ClientMapper.mapToClient;

@RestController
public class ClientController {
    private ClientService service;
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    public ClientController(ClientService service) {
        this.service = service;
    }

    @GetMapping("/c")
    public ResponseEntity<List<ClientDTO>> getClients() {
        logger.warn("Exposing all Clients");
        return ResponseEntity.ok(mapToClientDTOList(service.getClients()));
    }

    @GetMapping("/c/{id}")
    ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        return service.getClientById(id)
                .map(body -> ResponseEntity.ok(mapToClientDTO(body)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/c/{id}")
    public ResponseEntity<Client> addClient(@RequestBody ClientDTO clientDTO) {
        Client result = service.addClient(mapToClient(clientDTO));
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }


}