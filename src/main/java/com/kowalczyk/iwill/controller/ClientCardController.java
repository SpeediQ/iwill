package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.ClientCardDTO;
import com.kowalczyk.iwill.controller.dto.VisitDTO;
import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.service.ClientCardService;
import com.kowalczyk.iwill.service.VisitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ClientCardDTOMapper.mapClientCardToDTOList;
import static com.kowalczyk.iwill.controller.mapper.ClientCardMapper.mapToClientCard;
import static com.kowalczyk.iwill.controller.mapper.VisitDTOMapper.mapVisitToDTOList;
import static com.kowalczyk.iwill.controller.mapper.VisitMapper.mapToVisit;

@RestController
public class ClientCardController {
    private ClientCardService service;

    public ClientCardController(ClientCardService service) {
        this.service = service;
    }

    @GetMapping("/cc")
    public List<ClientCardDTO> getVisits() {
        return mapClientCardToDTOList(service.getClientCards());
    }

    @PostMapping("/cc/{id}")
    public ClientCard addVisit(@RequestBody ClientCardDTO clientCardDTO) {
        return service.addClientCard(mapToClientCard(clientCardDTO));
    }

    @PutMapping("/cc/{id}")
    public void updateVisit(@PathVariable Long id, @RequestBody ClientCardDTO clientCardDTO) {
        service.updateClientCard(mapToClientCard(clientCardDTO));
    }
}