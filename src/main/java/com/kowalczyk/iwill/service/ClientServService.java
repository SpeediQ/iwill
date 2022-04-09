package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.repesitory.ClientServRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServService {
    private final ClientServRepository repository;

    public ClientServService(ClientServRepository repository) {
        this.repository = repository;
    }

    public List<ClientServ> getClientServices() {
        return repository.findAll();
    }

    public ClientServ addClientService(ClientServ body) {
        return repository.save(body);
    }

    public void updateClientService(ClientServ body) {
        repository.save(body);
    }
}
