package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.repesitory.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> getClients() {
        return repository.findAll();
    }

    public Client addClient(Client body) {
        return repository.save(body);
    }

    public void updateClient(Client body) {
        repository.save(body);
    }

    public Optional<Client> getClientById (Long id){
        return repository.findById(id);
    }
}
