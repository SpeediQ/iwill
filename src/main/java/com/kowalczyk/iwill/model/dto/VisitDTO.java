package com.kowalczyk.iwill.model.dto;

import com.kowalczyk.iwill.model.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
public class VisitDTO {
    private int id;
    private String desc;
    private Set<ClientServDTO> clientServSet = new HashSet<>();
    private Date date;
    private String time;
    private Status status;
    private String title;
    private String code;
    private int promotion;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<ClientServDTO> getClientServSet() {
        return clientServSet;
    }

    public void setClientServSet(Set<ClientServDTO> clientServSet) {
        this.clientServSet = clientServSet;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return "VisitDTO{" +
                "desc='" + desc + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
