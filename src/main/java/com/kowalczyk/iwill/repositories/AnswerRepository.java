package com.kowalczyk.iwill.repositories;

import com.kowalczyk.iwill.model.Answer;
import com.kowalczyk.iwill.model.Question;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository {

    List<Answer> findAll();
    Question save(Answer entity);
    Optional<Answer> findById(Long id);
    boolean existsById(Long id);
}
