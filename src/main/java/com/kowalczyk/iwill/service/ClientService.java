package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.ConstanceNr;
import com.kowalczyk.iwill.model.ServiceType;
import com.kowalczyk.iwill.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Page<Client> findAllClientsPage(int pageNumber){
        Pageable pageable = PageRequest.of(pageNumber -1 , ConstanceNr.MAX_CLIENT_LIST_SIZE_5);
        return clientRepository.findAll(pageable);
    }


}
