package com.kowalczyk.iwill.repositories;

import com.kowalczyk.iwill.model.Question;
import com.kowalczyk.iwill.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {

    List<Role> findAll();
    Question save(Role entity);
    Optional<Role> findById(Long id);
    boolean existsById(Long id);
}
