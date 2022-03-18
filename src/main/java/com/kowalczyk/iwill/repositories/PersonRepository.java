package com.kowalczyk.iwill.repositories;

import com.kowalczyk.iwill.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    List<Person> findAll();

    Person save(Person entity);

    Optional<Person> findById(Long id);

    boolean existsById(Long id);

}
