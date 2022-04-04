package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.adapter.VisitRepository;
import com.kowalczyk.iwill.controller.dto.VisitDTO;
import com.kowalczyk.iwill.service.VisitService;
import com.kowalczyk.iwill.model.Visit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class VisitController {
    private static final Logger logger = LoggerFactory.getLogger(VisitController.class);
    private final VisitRepository repository;
    private final VisitService visitService;

    public VisitController(VisitRepository repository, VisitService visitService) {
        this.repository = repository;
        this.visitService = visitService;
    }

    @GetMapping("/v")
    List<Visit> getAll (@RequestBody Visit entity){
        List<Visit> result = repository.findAll();
        return result;
    }

    @PostMapping("/v")
    Visit createClient (Visit entity){
        Visit body = repository.save(entity);
        return body;
    }
    @GetMapping("/vd")
    List<VisitDTO> getAllDTO (){
        return visitService.getAllClient();
    }
}
