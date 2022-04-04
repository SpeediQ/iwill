package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.adapter.*;
import com.kowalczyk.iwill.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientCardController {
    private static final Logger logger = LoggerFactory.getLogger(ClientCardController.class);
    private final ClientCardRepository repository;


    public ClientCardController(ClientCardRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cc")
    List<ClientCard> getAll(@RequestBody ClientCard entity) {
        List<ClientCard> result = repository.findAll();
        return result;
    }

    @PostMapping("/cc")
    ClientCard createClient(ClientCard entity) {
        ClientCard body = repository.save(entity);
        return body;
    }

    @GetMapping("/cc/{id}")
    public ResponseEntity<ClientCard> getItem(@PathVariable Long id) {
        return repository.findById(id)
                .map(body -> ResponseEntity.ok(body))
                .orElse(ResponseEntity.notFound().build());
    }

    public ClientCard copyToNewItem(ClientCard body) {
        ClientCard newBody = new ClientCard();
        newBody.setDesc(body.getDesc());
        newBody.setVisits(body.getVisits());
//        newBody.setClient(body.getClient());
        return newBody;
    }
}
