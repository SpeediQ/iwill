package com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.Numerator;
import com.kowalczyk.iwill.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumeratorRepository extends JpaRepository<Numerator, Integer> {
}
