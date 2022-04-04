//package com.kowalczyk.iwill.controller;
//
//import com.kowalczyk.iwill.model.temp.Person;
//import com.kowalczyk.iwill.repositories.PersonRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.net.URI;
//import java.util.List;
//
//
//@RestController
//public class PersonController {
//    private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
//    private final PersonRepository repository;
//
//    public PersonController(PersonRepository repository) {
//        this.repository = repository;
//    }
//
//    @PostMapping("/person")
//    ResponseEntity<Person> createPerson(@RequestBody Person entity) {
//        Person result = repository.save(setAsNew(entity));
//        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
//    }
//
//    @GetMapping("/person")
//    ResponseEntity<List<Person>> findAll() {
//        logger.warn("Exposing all the Persons");
//        return ResponseEntity.ok(repository.findAll());
//    }
//
//    @GetMapping("/pa")
//    ResponseEntity<List<Person>> getAllActive() {
//        logger.warn("Exposing all Active Persons ");
//        return ResponseEntity.ok(repository.findAllByActiveIsTrue());
//    }
//
//    @GetMapping("/person/{id}")
//    ResponseEntity<Person> getPerson(@PathVariable Long id) {
//        return repository.findById(id)
//                .map(body -> ResponseEntity.ok(body))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PutMapping("/saf/{id}")
//    ResponseEntity<?> setActiveAsFalse(@PathVariable Long id) {
//        if (!repository.existsById(id)) {
//            return ResponseEntity.notFound().build();
//        }
//        repository.findById(id)
//                .ifPresent(body -> {
//                    body.setActive(false);
//                    repository.save(body);
//                });
//        return ResponseEntity.noContent().build();
//    }
//
//
//    @DeleteMapping(value = "/dp/{id}")
//    public ResponseEntity<Long> deleteById(@PathVariable Long id) {
//        try {
//            repository.deleteById(id);
//            return ResponseEntity.noContent().build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    private Person setAsNew(Person entity) {
//        entity.setActive(true);
//        return entity;
//    }
//
//    private Person setAsDeleted(Person entity) {
//        entity.setActive(false);
//        return entity;
//    }
//
//    // temp code
//    @GetMapping("/ep")
//    ResponseEntity<Person> createExamplePerson() {
//        Person example = new Person("Marcin", "Kowalczyk", 30);
//        Person result = repository.save(setAsNew(example));
//        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
//    }
//}
