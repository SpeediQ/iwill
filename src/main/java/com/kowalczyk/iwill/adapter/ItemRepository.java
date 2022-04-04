package com.kowalczyk.iwill.adapter;

import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAll();

    Item save(Item entity);

}
