package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repesitory.ClientServRepository;
import com.kowalczyk.iwill.repesitory.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitService {
    private final VisitRepository repository;

    public VisitService(VisitRepository repository) {
        this.repository = repository;
    }

    public List<Visit> getVisits() {
        return repository.findAll();
    }

    public Visit addVisit(Visit body) {
        return repository.save(body);
    }

    public void updateVisit(Visit body) {
        repository.save(body);
    }
}
