package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.CommentDTO;
import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.CommentDTOMapper.mapCommentToDTOList;
import static com.kowalczyk.iwill.controller.mapper.CommentMapper.mapToComment;

@RestController
public class CommentController {
    private CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping("/cm")
    public List<CommentDTO> getComments() {
        return mapCommentToDTOList(service.getComments());
    }

    @PostMapping("/cm/{id}")
    public Comment addComment(@RequestBody CommentDTO commentDTO) {
        return service.addComment(mapToComment(commentDTO));
    }

    @PutMapping("/cm/{id}")
    public void updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        service.updateComment(mapToComment(commentDTO));
    }
}