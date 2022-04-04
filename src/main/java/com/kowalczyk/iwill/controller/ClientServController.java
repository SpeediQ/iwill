package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.adapter.ClientServRepository;
import com.kowalczyk.iwill.controller.dto.ClientServDTO;
import com.kowalczyk.iwill.model.ClientServ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ClientServMapper.mapToClientServ;

@RestController
public class ClientServController {
    private static final Logger logger = LoggerFactory.getLogger(ClientServController.class);
    public static final Long EMPTY_ID = null;
    private final ClientServRepository repository;

    public ClientServController(ClientServRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cs")
    List<ClientServ> getAll (@RequestBody ClientServ entity){
        List<ClientServ> result = repository.findAll();
        return result;
    }
    @PutMapping("/cs/{id}")
    public void updateClientServ(@PathVariable Long id, @RequestBody ClientServDTO clientServDTO) {
        ClientServ clientServ = new ClientServ();
        clientServ.setId(id);
        clientServ.setDesc(clientServDTO.getDesc());
        clientServ.setItem(clientServDTO.getItem());
        clientServ.setComment(clientServDTO.getComment());
        repository.save(mapToClientServ(id, clientServDTO));


    }

    @PostMapping("/ccs")
    public ClientServ createClientServ(@RequestBody ClientServDTO clientServDTO) {

        return repository.save(mapToClientServ(EMPTY_ID, clientServDTO));
    }



    @PostMapping("/cs")
    ClientServ createClient (ClientServ entity){
        ClientServ body = repository.save(entity);
        return body;
    }
}
