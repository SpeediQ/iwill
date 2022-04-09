package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.CommentDTO;
import com.kowalczyk.iwill.model.Comment;

import static com.kowalczyk.iwill.controller.mapper.ItemMapper.mapToItem;

public class CommentMapper {
    public static Comment mapToComment(CommentDTO commentDTO){
        return Comment.builder()
                .id(commentDTO.getId())
                .desc(commentDTO.getDesc())
                .item(mapToItem(commentDTO.getItem()))
                .build();

    }
}
