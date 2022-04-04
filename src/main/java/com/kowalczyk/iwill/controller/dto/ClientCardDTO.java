package com.kowalczyk.iwill.controller.dto;


import com.kowalczyk.iwill.model.Visit;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
public class ClientCardDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String desc;
    @OneToMany(mappedBy = "clientCard", cascade= CascadeType.ALL)
    private List<Visit> visits;

}
