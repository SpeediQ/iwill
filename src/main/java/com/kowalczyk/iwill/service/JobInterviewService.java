//package com.kowalczyk.iwill.logic;
//
//import com.kowalczyk.iwill.model.temp.JobInterview;
//import com.kowalczyk.iwill.model.temp.Question;
//import com.kowalczyk.iwill.repositories.AnswerRepository;
//import com.kowalczyk.iwill.repositories.JobInterviewRepository;
//import com.kowalczyk.iwill.repositories.QuestionRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.net.URI;
//import java.util.LinkedList;
//import java.util.List;
//
//
//@RestController
//public class JobInterviewService {
//    private static final Logger logger = LoggerFactory.getLogger(JobInterviewService.class);
//    private final JobInterviewRepository repository;
//    private final QuestionRepository questionRepository;
//    private final AnswerRepository answerRepository;
//
//    public JobInterviewService(JobInterviewRepository repository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
//        this.repository = repository;
//        this.questionRepository = questionRepository;
//        this.answerRepository = answerRepository;
//    }
//
//
//    @PostMapping("/ji")
//    ResponseEntity<JobInterview> createPerson(@RequestBody JobInterview entity) {
//        JobInterview result = repository.save(setAsNew(entity));
//        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
//    }
//
//    @GetMapping("/ji")
//    ResponseEntity<List<JobInterview>> findAll() {
//        logger.warn("Exposing all the JobInterview");
//        return ResponseEntity.ok(repository.findAll());
//    }
//
//    private JobInterview setAsNew(JobInterview entity) {
//        entity.setActive(true);
//        return entity;
//    }
//
//    @GetMapping("/jobinterview/{id}")
//    ResponseEntity<JobInterview> getJobInterview(@PathVariable Long id) {
//        return repository.findById(id)
//                .map(body -> ResponseEntity.ok(body))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//
//    @PostMapping("/ceji")
//    ResponseEntity<JobInterview> createExampleJobInterview() {
//        JobInterview jobInterview = new JobInterview();
//        jobInterview.setDesc("alloha");
//
//        QuestionService questionService = new QuestionService(questionRepository, answerRepository);
//        Question question ;
//
//        List<Question> questionsList = new LinkedList<>();
//
//        for (int i = 0; i < 5; i++) {
//            question = questionService.createExampleQuestion().getBody();
//            question.setDesc(question.getDesc()+" v "+i);
//            questionsList.add(question);
//            questionRepository.save(question);
//        }
//
//        jobInterview.setQuestion(questionsList);
//        JobInterview result = repository.save(jobInterview);
//        result.getQuestion().get(0).setDesc("ddupa");
//        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
//    }
//}
