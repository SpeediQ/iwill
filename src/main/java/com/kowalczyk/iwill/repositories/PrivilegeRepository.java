package com.kowalczyk.iwill.repositories;

import com.kowalczyk.iwill.model.Privilege;
import com.kowalczyk.iwill.model.Question;

import java.util.List;
import java.util.Optional;

public interface PrivilegeRepository {

    List<Privilege> findAll();
    Question save(Privilege entity);
    Optional<Privilege> findById(Long id);
    boolean existsById(Long id);
}
