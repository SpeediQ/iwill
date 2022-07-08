package com.kowalczyk.iwill.model;

import javax.persistence.*;

@Entity
public class ServiceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @Column(name = "DESCRIPTION")
    private String desc;

    private String name;

    private double value;


    public ServiceType() {
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ServiceType{" +
                "name='" + name + '\'' +
                '}';
    }

    public String niceString() {
        return "Tytuł: " + name + " ; Cena: " + value + " ; Opis: " + desc;
    }
}
