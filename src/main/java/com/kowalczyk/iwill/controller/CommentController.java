package com.kowalczyk.iwill.controller;

import com.kowalczyk.iwill.controller.dto.CommentDTO;
import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.CommentDTOMapper.mapToCommentDTO;
import static com.kowalczyk.iwill.controller.mapper.CommentDTOMapper.mapToCommentDTOList;
import static com.kowalczyk.iwill.controller.mapper.CommentMapper.mapToComment;

@RestController
public class CommentController {
    private CommentService service;
    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);


    public CommentController(CommentService service) {
        this.service = service;
    }

    @GetMapping("/cm")
    public ResponseEntity<List<CommentDTO>> getComments() {
        logger.warn("Exposing all Comments");
        return ResponseEntity.ok(mapToCommentDTOList(service.getComments()));
    }

    @GetMapping("/cm/{id}")
    ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id) {
        return service.getCommentById(id)
                .map(body -> ResponseEntity.ok(mapToCommentDTO(body)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/cm/{id}")
    public ResponseEntity<Comment> addComment(@RequestBody CommentDTO commentDTO) {
        Comment result = service.addComment(mapToComment(commentDTO));
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

}