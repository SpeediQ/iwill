package com.kowalczyk.iwill.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import org.springframework.context.event.EventListener;

@Data
@Builder
public class ClientServDTO {
    private long id;
    private String desc;
    private CommentDTO commentDTO;
}
