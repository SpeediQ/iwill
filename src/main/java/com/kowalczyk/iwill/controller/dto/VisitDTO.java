package com.kowalczyk.iwill.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VisitDTO {
    private long id;
    private String desc;
    private List<ClientServDTO> clientServDTOS;
//    private long clientCardId;
}
