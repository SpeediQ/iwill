package com.kowalczyk.iwill.controller.dto;

import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.model.Item;
import lombok.Data;

@Data
public class ClientServDTO {
    private long id;
    private String desc;
    private Item item;
    private Comment comment;
}
