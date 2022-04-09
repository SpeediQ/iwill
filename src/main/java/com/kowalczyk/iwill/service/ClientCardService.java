package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repesitory.ClientCardRepository;
import com.kowalczyk.iwill.repesitory.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
