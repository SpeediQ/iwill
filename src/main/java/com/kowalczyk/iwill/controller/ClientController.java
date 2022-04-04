package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.adapter.ClientRepository;
import com.kowalczyk.iwill.controller.dto.ClientDTO;
import com.kowalczyk.iwill.service.ClientService;
import com.kowalczyk.iwill.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientController {
    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);
    private final ClientRepository repository;
    private ClientService clientService;



    public ClientController(ClientRepository repository, ClientService clientService) {
        this.repository = repository;
        this.clientService = clientService;
    }

    @GetMapping("/c")
    List<Client> getAll (){
        List<Client> result = repository.findAll();
        return result;
    }

    @GetMapping("/cd")
    List<ClientDTO> getAllDTO (){
        return clientService.getAllClient();
    }

    @PostMapping("/c")
    Client createClient (Client client){
        Client body = repository.save(client);
        return body;
    }
    @GetMapping("/c/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return repository.findById(id)
                .map(body -> ResponseEntity.ok(body))
                .orElse(ResponseEntity.notFound().build());
    }
    public Client copyToNewItem(Client body) {
        Client newBody = new Client();
        newBody.setDesc(body.getDesc());
//        newBody.setClientCard(body.getClientCard());
        newBody.setFirstname(body.getFirstname());
        newBody.setLastname(body.getLastname());
        return newBody;
    }


}
