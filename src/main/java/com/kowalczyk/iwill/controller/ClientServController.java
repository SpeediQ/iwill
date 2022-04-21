package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.ClientServDTO;
import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.service.ClientServService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ClientServDTOMapper.mapToClientServDTO;
import static com.kowalczyk.iwill.controller.mapper.ClientServDTOMapper.mapToClientServDTOList;
import static com.kowalczyk.iwill.controller.mapper.ClientServMapper.mapToClientServ;

@RestController
public class ClientServController {
    private ClientServService service;
    private static final Logger logger = LoggerFactory.getLogger(ClientServService.class);

    public ClientServController(ClientServService service) {
        this.service = service;
    }

    @GetMapping("/cs")
    public ResponseEntity<List<ClientServDTO>> getClientServices() {
        logger.warn("Exposing all ClientServices");
        return ResponseEntity.ok(mapToClientServDTOList(service.getClientServices()));
    }

    @GetMapping("/cs/{id}")
    ResponseEntity<ClientServDTO> getClientServiceById(@PathVariable Long id) {
        return service.getClientServiceById(id)
                .map(body -> ResponseEntity.ok(mapToClientServDTO(body)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cs/{id}")
    public ResponseEntity<ClientServ> addClientService(@RequestBody ClientServDTO clientServDTO) {
        ClientServ result = service.addClientService(mapToClientServ(clientServDTO));
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

}