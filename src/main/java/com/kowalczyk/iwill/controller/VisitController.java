package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.VisitDTO;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.service.VisitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.VisitDTOMapper.mapToVisitDTO;
import static com.kowalczyk.iwill.controller.mapper.VisitDTOMapper.mapToVisitDTOList;
import static com.kowalczyk.iwill.controller.mapper.VisitMapper.mapToVisit;

@RestController
public class VisitController {
    private VisitService service;
    private static final Logger logger = LoggerFactory.getLogger(VisitService.class);


    @GetMapping("/v")
    public ResponseEntity<List<VisitDTO>> getVisits() {
        logger.warn("Exposing all Visits");
        return ResponseEntity.ok(mapToVisitDTOList(service.getVisits()));
    }

    @GetMapping("/v/{id}")
    ResponseEntity<VisitDTO> getVisitById(@PathVariable Long id) {
        return service.getVisitById(id)
                .map(body -> ResponseEntity.ok(mapToVisitDTO(body)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/v/{id}")
    public ResponseEntity<Visit> addVisit(@RequestBody VisitDTO visitDTO) {
        Visit result = service.addVisit(mapToVisit(visitDTO));
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }


}