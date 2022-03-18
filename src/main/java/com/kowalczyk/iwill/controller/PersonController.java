package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.model.Person;
import com.kowalczyk.iwill.model.Question;
import com.kowalczyk.iwill.repositories.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;


@RestController
public class PersonController {
    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private final PersonRepository repository;

    public PersonController(PersonRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/persons")
    ResponseEntity<Person> createPerson(@RequestBody Person entity) {
        Person result = repository.save(setAsNew(entity));
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @GetMapping("/persons")
    ResponseEntity<List<Person>> findAll() {
        logger.warn("Exposing all the Persons");
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/person/{id}")
    ResponseEntity<Person> getPerson(@PathVariable Long id) {
        return repository.findById(id)
                .map(body -> ResponseEntity.ok(body))
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/dp/{id}")
    ResponseEntity<Person> deletePerson(@PathVariable Long id) {
        return repository.findById(id)
                .map(body -> ResponseEntity.ok(setAsDeleted(body)))
                .orElse(ResponseEntity.notFound().build());
    }


    private Person setAsNew(Person entity){
        entity.setActive(true);
        return entity;
    }

    private Person setAsDeleted(Person entity){
        entity.setActive(false);
        return entity;
    }

    // temp code
    @GetMapping("/ep")
    ResponseEntity<Person> createExamplePerson() {
        Person example = new Person("Marcin", "Kowalczyk", 30);
        Person result = repository.save(setAsNew(example));
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }
}
