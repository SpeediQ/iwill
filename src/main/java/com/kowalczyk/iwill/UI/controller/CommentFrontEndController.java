package com.kowalczyk.iwill.UI.controller;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.service.ClientCardService;
import com.kowalczyk.iwill.service.ClientService;
import com.kowalczyk.iwill.service.CommentService;
import com.kowalczyk.iwill.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class CommentFrontEndController {

    CommentService commentService;
    ItemService itemService;

    public CommentFrontEndController(CommentService commentService, ItemService itemService) {
        this.commentService = commentService;
        this.itemService = itemService;
    }

    @GetMapping("/registerComment")
    public String showForm(Model model) {
        Comment comment = new Comment();
        model.addAttribute("comment", comment);

        List<Item> items = itemService.getItems();
        model.addAttribute("items", items);

        return "register_comment_form";
    }

    @PostMapping("/registerComment")
    public String submitForm(@ModelAttribute("comment") Comment comment) {
        commentService.addComment(comment);
        return "register_comment_success";
    }

}
