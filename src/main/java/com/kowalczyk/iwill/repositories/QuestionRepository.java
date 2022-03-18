package com.kowalczyk.iwill.repositories;

import com.kowalczyk.iwill.model.Person;
import com.kowalczyk.iwill.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    List<Question> findAll();
    Question save(Question entity);
    Optional<Question> findById(Long id);
    boolean existsById(Long id);
}
