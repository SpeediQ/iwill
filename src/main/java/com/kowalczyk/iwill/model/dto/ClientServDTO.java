package com.kowalczyk.iwill.model.dto;

import com.kowalczyk.iwill.model.Item;
import com.kowalczyk.iwill.model.Visit;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
public class ClientServDTO {

    private int id;
    private String desc;
    private Visit visit;
    private String title;
    private double price;

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

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ClientServDTO{" +
                "desc='" + desc + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
