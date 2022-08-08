package com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.ServiceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Integer> {
    Page<Client> findAllByActiveIsTrue(Pageable pageable);
}

