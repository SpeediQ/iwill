package com.kowalczyk.iwill.adapter;

import com.kowalczyk.iwill.model.Person;
import com.kowalczyk.iwill.repositories.PersonRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlPersonRepository extends PersonRepository, JpaRepository<Person, Long> {


}
