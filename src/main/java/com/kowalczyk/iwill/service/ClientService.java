package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.ConstanceNr;
import com.kowalczyk.iwill.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Page<Client> findAllClientsPage(int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, ConstanceNr.MAX_CLIENT_LIST_SIZE_5);
        return clientRepository.findAll(pageable);
    }

    public Page<Client> findAllSorteredClientsPage(int pageNumber, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, ConstanceNr.MAX_CLIENT_LIST_SIZE_5, sort);
        return clientRepository.findAll(pageable);
    }

    public Page<Client> findAllSorteredClientsManagerPage(int pageNumber, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, ConstanceNr.MAX_CLIENT_LIST_SIZE_10, sort);
        return clientRepository.findAll(pageable);
    }


}
