package com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.ClientCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientCardRepository extends JpaRepository<ClientCard, Integer> {
}
