package com.kowalczyk.iwill.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
    private long id;
    private String desc;
    private ItemDTO itemDTO;
}