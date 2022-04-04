package com.kowalczyk.iwill.adapter;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.ClientCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientCardRepository extends JpaRepository<ClientCard, Long> {
    List<ClientCard> findAll();

    ClientCard save(ClientCard entity);

}
