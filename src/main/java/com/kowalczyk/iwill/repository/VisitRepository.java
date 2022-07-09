package com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Integer> {

    @Query("select v from Visit v where v.status.id = ?1")
    List <Visit> getAllReservationsByStatus(Integer id);
}
