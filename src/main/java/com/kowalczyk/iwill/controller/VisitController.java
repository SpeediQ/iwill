package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.VisitDTO;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.service.VisitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.VisitDTOMapper.mapVisitToDTOList;
import static com.kowalczyk.iwill.controller.mapper.VisitMapper.mapToVisit;

@RestController
public class VisitController {
    private VisitService service;

    public VisitController(VisitService service) {
        this.service = service;
    }

    @GetMapping("/v")
    public List<VisitDTO> getVisits() {
        return mapVisitToDTOList(service.getVisits());
    }

    @PostMapping("/v/{id}")
    public Visit addVisit(@RequestBody VisitDTO visitDTO) {
        return service.addVisit(mapToVisit(visitDTO));
    }

    @PutMapping("/v/{id}")
    public void updateVisit(@PathVariable Long id, @RequestBody VisitDTO visitDTO) {
        service.updateVisit(mapToVisit(visitDTO));
    }
}