package com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.ClientServ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientServRepository extends JpaRepository<ClientServ, Integer> {
    @Query("select distinct cs from ClientServ cs where cs.serviceType.id = ?1")
    List<ClientServ> getServicesByItemId(Integer id);

    @Query("select count(cs) from ClientServ cs where cs.serviceType.id = ?1")
    int getItemUsageCounter(Integer id);
}
