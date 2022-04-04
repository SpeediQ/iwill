package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.adapter.CommentRepository;
import com.kowalczyk.iwill.adapter.ItemRepository;
import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.model.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommentController {
    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentRepository repository;

    public CommentController(CommentRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/cm")
    List<Comment> getAll (@RequestBody Comment entity){
        List<Comment> result = repository.findAll();
        return result;
    }

    @PostMapping("/cm")
    Comment createClient (Comment entity){
        Comment body = repository.save(entity);
        return body;
    }
}
