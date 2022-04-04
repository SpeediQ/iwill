package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.adapter.VisitRepository;
import com.kowalczyk.iwill.controller.dto.VisitDTO;
import com.kowalczyk.iwill.model.Visit;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VisitService {
    private VisitRepository repository;

    public VisitService(VisitRepository repository) {
        this.repository = repository;
    }

    public List<VisitDTO> getAllClient(){
        return repository.findAll()
                .stream()
                .map(body -> convertClientToClientDTO(body))
                .collect(Collectors.toList());


    }

    private VisitDTO convertClientToClientDTO(Visit body) {
        VisitDTO bodyDTO = new VisitDTO();
        bodyDTO.setId(body.getId());
        bodyDTO.setDesc(body.getDesc());
        bodyDTO.setClientServs(body.getClientServs());

        return bodyDTO;
    }

}
