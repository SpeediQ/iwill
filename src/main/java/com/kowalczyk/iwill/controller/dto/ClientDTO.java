package com.kowalczyk.iwill.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDTO {
    private long id;
    private String firstname;
    private String lastname;
    private String desc;
    private ClientCardDTO clientCardDTO;
}
