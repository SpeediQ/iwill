package com.kowalczyk.iwill.controller.dto;

import com.kowalczyk.iwill.model.Item;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class CommentDTO {
    private long id;
    private String desc;
    private ItemDTO item;
}