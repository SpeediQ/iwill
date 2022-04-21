package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.repesitory.ClientCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientCardService {
    private final ClientCardRepository repository;

    public ClientCardService(ClientCardRepository repository) {
        this.repository = repository;
    }

    public List<ClientCard> getClientCards() {
        return repository.findAll();
    }

    public ClientCard addClientCard(ClientCard body) {
        return repository.save(body);
    }

    public void updateClientCard(ClientCard body) {
        repository.save(body);
    }

    public Optional<ClientCard> getClientCardById(Long id) {
        return repository.findById(id);
    }

}
