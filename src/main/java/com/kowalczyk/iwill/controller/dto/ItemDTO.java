package com.kowalczyk.iwill.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDTO {
    private long id;
    private String type;
    private String title;
    private String desc;
    private Double price;

}
