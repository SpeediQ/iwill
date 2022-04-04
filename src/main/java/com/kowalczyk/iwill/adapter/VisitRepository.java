package com.kowalczyk.iwill.adapter;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findAll();

    Visit save(Visit entity);

}
