package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.model.JobInterview;
import com.kowalczyk.iwill.model.Person;
import com.kowalczyk.iwill.repositories.JobInterviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
public class JobInterviewController {
    private static final Logger logger = LoggerFactory.getLogger(JobInterviewController.class);
    private final JobInterviewRepository repository;

    public JobInterviewController(JobInterviewRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/ji")
    ResponseEntity<JobInterview> createPerson(@RequestBody JobInterview entity) {
        JobInterview result = repository.save(setAsNew(entity));
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping("/ji")
    ResponseEntity<List<JobInterview>> findAll() {
        logger.warn("Exposing all the JobInterview");
        return ResponseEntity.ok(repository.findAll());
    }
    private JobInterview setAsNew(JobInterview entity){
        entity.setActive(true);
        return entity;
    }

    @GetMapping("/jobinterview/{id}")
    ResponseEntity<JobInterview> getJobInterview(@PathVariable Long id) {
        return repository.findById(id)
                .map(body -> ResponseEntity.ok(body))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/eji")
    ResponseEntity<JobInterview> createExampleJobInterview() {
        JobInterview example = new JobInterview("ING", 4000L, false);
        JobInterview result = repository.save(setAsNew(example));
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
}
