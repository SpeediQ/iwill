package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.CommentDTO;
import com.kowalczyk.iwill.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.mapper.ItemMapper.mapToItem;

public class CommentMapper {
    public static List<Comment> mapToCommentList(List<CommentDTO> commentDTOS) {
        return commentDTOS.stream()
                .map(CommentMapper::mapToComment)
                .collect(Collectors.toList());
    }

    public static Comment mapToComment(CommentDTO commentDTO) {
        return Comment.builder()
                .id(commentDTO.getId())
                .desc(commentDTO.getDesc())
                .item(mapToItem(commentDTO.getItemDTO()))
                .build();

    }
}
