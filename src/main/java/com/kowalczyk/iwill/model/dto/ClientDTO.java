package com.kowalczyk.iwill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.Date;

@Builder
@AllArgsConstructor
public class ClientDTO {

    private int id;
    private String name;
    private String lastname;
    private String comment;
    private ClientCardDTO clientCardDTO;
    private Date date;
    private String code;
    private Boolean active;


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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "name='" + name + '\'' +
                '}';
    }
}
