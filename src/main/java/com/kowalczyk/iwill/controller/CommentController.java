package com.kowalczyk.iwill.controller;


import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.repository.ClientServRepository;
import com.kowalczyk.iwill.repository.CommentRepository;
import com.kowalczyk.iwill.repository.ItemRepository;
import com.kowalczyk.iwill.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@Controller
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/comments")
    public String listVisits(Model model){
        List<Comment> listComment = commentRepository.findAll();
        model.addAttribute("listComment", listComment);
        return "comments";
    }

    @GetMapping("/comments/new")
    public String showVisitNewForm(Model model){
        model.addAttribute("comment", new Comment());
        return "comment_form";
    }

    @PostMapping("/comments/save")
    public String saveVisit(Comment comment){
        commentRepository.save(comment);
        return "redirect:/comments";
    }

    @GetMapping("/comments/edit/{id}")
    public String showClientServEditForm(@PathVariable("id") Integer id, Model model) {
        Comment comment = commentRepository.findById(id).get();
        model.addAttribute("comment", comment);
        return "comment_form";

    }



}
