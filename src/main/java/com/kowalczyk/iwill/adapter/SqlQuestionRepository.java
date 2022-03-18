package com.kowalczyk.iwill.adapter;

import com.kowalczyk.iwill.model.Person;
import com.kowalczyk.iwill.model.Question;
import com.kowalczyk.iwill.repositories.PersonRepository;
import com.kowalczyk.iwill.repositories.QuestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlQuestionRepository extends QuestionRepository, JpaRepository<Question, Long> {


}
