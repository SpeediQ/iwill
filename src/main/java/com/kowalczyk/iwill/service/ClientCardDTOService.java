package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.adapter.ClientCardRepository;
import com.kowalczyk.iwill.adapter.ClientRepository;
import com.kowalczyk.iwill.controller.dto.ClientCardDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientCardDTOService {
    private ClientCardRepository repository;

    public ClientCardDTOService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<ClientCardDTO> getAllClient(){
        return repository.findAll()
                .stream()
                .map(body -> convertClientToClientDTO(body))
                .collect(Collectors.toList());


    }

    private ClientCardDTO convertClientToClientDTO(ClientCardDTO body) {
        ClientCardDTO newBody = new ClientCardDTO();
//        newBody.setDesc(newBody.getDesc());
//        newBody.setFirstname(newBody.getFirstname());
//        newBody.setLastname(newBody.getLastname());
//        newBody.setId(body.getId());
//        newBody.setClientCardDTO();

        return newBody;
    }

//    private

}
