package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ClientServDTO;
import com.kowalczyk.iwill.controller.dto.CommentDTO;
import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.mapper.ItemDTOMapper.mapToItemDTO;

public class CommentDTOMapper {
    public static List<CommentDTO> mapCommentToDTOList(List<Comment> comments) {
        return comments.stream()
                .map(comment -> mapToCommentDTO(comment))
                .collect(Collectors.toList());
    }


    public static CommentDTO mapToCommentDTO(Comment comment) {
        return CommentDTO.builder()
                .id(comment.getId())
                .desc(comment.getDesc())
                .item(mapToItemDTO(comment.getItem()))
                .build();
    }
}
