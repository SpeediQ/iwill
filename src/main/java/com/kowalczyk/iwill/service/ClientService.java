package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.adapter.ClientRepository;
import com.kowalczyk.iwill.controller.dto.ClientDTO;
import com.kowalczyk.iwill.model.Client;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public List<ClientDTO> getAllClient(){
        return clientRepository.findAll()
                .stream()
                .map(client -> convertClientToClientDTO(client))
                .collect(Collectors.toList());


    }

    private ClientDTO convertClientToClientDTO(Client client) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(client.getId());
        clientDTO.setDesc(client.getDesc());
        clientDTO.setFirstname(client.getFirstname());
        clientDTO.setLastname(client.getLastname());

        return clientDTO;
    }

}
