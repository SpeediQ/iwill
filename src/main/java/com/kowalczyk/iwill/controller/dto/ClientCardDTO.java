package com.kowalczyk.iwill.controller.dto;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.Visit;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
public class ClientCardDTO {
    private long id;
    private String desc;
    private List<VisitDTO> visits;
}
