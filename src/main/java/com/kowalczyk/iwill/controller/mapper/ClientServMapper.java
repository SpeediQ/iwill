package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ClientServDTO;
import com.kowalczyk.iwill.model.ClientServ;

public class ClientServMapper {
    public static ClientServ mapToClientServ(long id, ClientServDTO clientServDTO) {
        return ClientServ.ClientServBuilder.aClientServ()
                .withId(id)
                .withComment(clientServDTO.getComment())
                .withDesc(clientServDTO.getDesc())
                .withComment(clientServDTO.getComment())
                .build();
    }
}
