package com.kowalczyk.iwill.adapter;

import com.kowalczyk.iwill.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAll();

    Client save(Client entity);

}
