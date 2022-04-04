package com.kowalczyk.iwill.controller.dto;


import com.kowalczyk.iwill.model.ClientServ;
import lombok.Data;

import java.util.List;

@Data
public class VisitDTO {
    private long id;
    private String desc;
    private List<ClientServ> clientServs;
}
