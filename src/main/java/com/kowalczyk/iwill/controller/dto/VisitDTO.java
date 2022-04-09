package com.kowalczyk.iwill.controller.dto;

import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.ClientServ;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
public class VisitDTO {
    private long id;
    private String desc;
    private List<ClientServDTO> clientServs;
}
