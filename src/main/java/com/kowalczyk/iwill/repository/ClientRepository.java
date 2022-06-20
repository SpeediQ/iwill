package com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
}
