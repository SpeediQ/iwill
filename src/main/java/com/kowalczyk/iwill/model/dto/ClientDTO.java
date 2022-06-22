package com.kowalczyk.iwill.model.dto;

import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.Visit;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
public class ClientDTO {

    private int id;
    private String name;
    private String lastname;
    private String comment;
    private ClientCardDTO clientCardDTO;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public ClientCardDTO getClientCardDTO() {
        return clientCardDTO;
    }

    public void setClientCardDTO(ClientCardDTO clientCardDTO) {
        this.clientCardDTO = clientCardDTO;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
