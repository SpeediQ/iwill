package com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
