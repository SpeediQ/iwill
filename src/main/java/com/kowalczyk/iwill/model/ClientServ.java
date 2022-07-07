package com.kowalczyk.iwill.model;

import javax.persistence.*;

@Entity
public class ClientServ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "DESCRIPTION")
    private String desc;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VISIT_ID")
    private Visit visit;

    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SERVICE_TYPE_ID")
    private ServiceType serviceType;

    private double price;


    public ClientServ() {
    }



    public ClientServ(Visit visit) {
        this.visit = visit;
    }

    public ClientServ(ServiceType serviceType) {
        this.serviceType = serviceType;
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

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return " {" + title + " " + desc+ "} ";
    }
}
