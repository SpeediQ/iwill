package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.repesitory.ClientServRepository;
import com.kowalczyk.iwill.repesitory.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> getComments() {
        return repository.findAll();
    }

    public Comment addComment(Comment body) {
        return repository.save(body);
    }

    public void updateComment(Comment body) {
        repository.save(body);
    }
}
