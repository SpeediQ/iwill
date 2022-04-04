package com.kowalczyk.iwill.controller.dto;


import lombok.Data;

@Data
public class ClientDTO {
    private long id;
    private String firstname;
    private String lastname;
    private String desc;
    private ClientCardDTO clientCard;
}
