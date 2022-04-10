package com.kowalczyk.iwill.repesitory;

import com.kowalczyk.iwill.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
