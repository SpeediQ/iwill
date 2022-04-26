package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repesitory.VisitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VisitService {
    private final VisitRepository repository;

    public VisitService(VisitRepository repository) {
        this.repository = repository;
    }

    public List<Visit> getVisits() {
        return repository.findAll();
    }
    public List<Visit> getVisitByClientCardId(Long id) {
        return repository.findAllByClientCard_Id(id);
    }

    public Visit addVisit(Visit body) {
        return repository.save(body);
    }

    public void updateVisit(Visit body) {
        repository.save(body);
    }

    public Optional<Visit> getVisitById(Long id) {
        return repository.findById(id);
    }


}
