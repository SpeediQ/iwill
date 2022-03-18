package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.model.Person;
import com.kowalczyk.iwill.model.Question;
import com.kowalczyk.iwill.repositories.PersonRepository;
import com.kowalczyk.iwill.repositories.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionRepository repository;

    public QuestionController(QuestionRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/q")
    ResponseEntity<Question> createPerson(@RequestBody Question entity) {
        Question result = repository.save(setAsNew(entity));
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
    @GetMapping("/q")
    ResponseEntity<List<Question>> findAll() {
        logger.warn("Exposing all the Questions");
        return ResponseEntity.ok(repository.findAll());
    }
    @GetMapping("/question/{id}")
    ResponseEntity<Question> readQuestion(@PathVariable Long id) {
        return repository.findById(id)
                .map(body -> ResponseEntity.ok(body))
                .orElse(ResponseEntity.notFound().build());
    }

    private Question setAsNew(Question entity){
        entity.setActive(true);
        return entity;
    }

    @GetMapping("/eq")
    ResponseEntity<Question> createExamplePerson() {
        Question example = new Question("ABC",false);
        example.setActive(true);
        Question result = repository.save(setAsNew(example));
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
}
