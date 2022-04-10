package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.repesitory.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
