package com.kowalczyk.iwill.controller.dto;

import com.kowalczyk.iwill.model.ClientCard;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
public class ClientDTO {
    private long id;
    private String firstname;
    private String lastname;
    private String desc;
    private ClientCardDTO clientCardDTO;
}
