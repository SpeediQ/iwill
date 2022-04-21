package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.ClientCardDTO;
import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.service.ClientCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ClientCardDTOMapper.mapToClientCardDTO;
import static com.kowalczyk.iwill.controller.mapper.ClientCardDTOMapper.mapToClientCardDTOList;
import static com.kowalczyk.iwill.controller.mapper.ClientCardMapper.mapToClientCard;

@RestController
public class ClientCardController {
    private ClientCardService service;
    private static final Logger logger = LoggerFactory.getLogger(ClientCardController.class);


    public ClientCardController(ClientCardService service) {
        this.service = service;
    }

    @GetMapping("/cc")
    public ResponseEntity<List<ClientCardDTO>> getVisits() {
        logger.warn("Exposing all ClientCards");
        return ResponseEntity.ok(mapToClientCardDTOList(service.getClientCards()));
    }

    @GetMapping("/cc/{id}")
    ResponseEntity<ClientCardDTO> getClientCardById(@PathVariable Long id) {
        return service.getClientCardById(id)
                .map(body -> ResponseEntity.ok(mapToClientCardDTO(body)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cc/{id}")
    public ResponseEntity<ClientCard> addVisit(@RequestBody ClientCardDTO clientCardDTO) {
        ClientCard result = service.addClientCard(mapToClientCard(clientCardDTO));
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }


}