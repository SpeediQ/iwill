//package com.kowalczyk.iwill.logic;
//
//import com.kowalczyk.iwill.model.temp.Answer;
//import com.kowalczyk.iwill.model.temp.Question;
//import com.kowalczyk.iwill.repositories.AnswerRepository;
//import com.kowalczyk.iwill.repositories.QuestionRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//import java.util.List;
//
//
//@RestController
//public class QuestionService {
//    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);
//    private QuestionRepository questionRepository;
//    private AnswerRepository answerRepository;
//
//    public QuestionService(QuestionRepository questionRepository, AnswerRepository answerRepository) {
//        this.questionRepository = questionRepository;
//        this.answerRepository = answerRepository;
//    }
//
//    @PostMapping("/q")
//    ResponseEntity<Question> createPerson(@RequestBody Question entity) {
//        Question result = questionRepository.save(setAsNew(entity));
//        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
//    }
//    @GetMapping("/q")
//    ResponseEntity<List<Question>> findAll() {
//        logger.warn("Exposing all the Questions");
//        return ResponseEntity.ok(questionRepository.findAll());
//    }
//    @GetMapping("/question/{id}")
//    ResponseEntity<Question> readQuestion(@PathVariable Long id) {
//        return questionRepository.findById(id)
//                .map(body -> ResponseEntity.ok(body))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    private Question setAsNew(Question entity){
//        entity.setActive(true);
//        return entity;
//    }
//
//    @PostMapping("/ceq")
//    ResponseEntity<Question> createExampleQuestion() {
//        Answer answer = new Answer();
//        answer.setDesc("abcde");
//        answerRepository.save(answer);
//
//        Question example = new Question("ABC",false, answer);
//        example.setActive(true);
//
//        Question result = questionRepository.save(setAsNew(example));
//        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
//    }
//
//
//}
