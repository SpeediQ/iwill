package com.kowalczyk.iwill.model;

//import javax.persistence.*;
//import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.text.DecimalFormat;

@Entity
public class ClientServ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "DESCRIPTION")
    private String desc;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "VISIT_ID")
    private Visit visit;

    private String title;

    @OneToOne
    @JoinColumn(name = "SERVICE_TYPE_ID")
    private ServiceType serviceType;

    private double price;

    private int promotion;
    private double finalPrice;


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

    public int getPromotion() {
        return promotion;
    }

    public void setPromotion(int promotion) {
        this.promotion = promotion;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getFinalPriceIncludingPromotion() {
        DecimalFormat decimalFormat = new DecimalFormat("##.00");
        double sumIncludingPromotion = getPrice() * (1 - getPromotion() * 0.01);
        return Math.round(sumIncludingPromotion * 100.0) / 100.0;
    }

    public String getOneLineNiceStringForPrice() {
        double finalPriceIncludingPromotion = getFinalPriceIncludingPromotion();
        return finalPriceIncludingPromotion + " zł (rabat na usługę: " + getPromotion() + " %)";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientServ)) return false;

        ClientServ that = (ClientServ) o;

        if (id != that.id) return false;
        if (Double.compare(that.price, price) != 0) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        return serviceType != null ? serviceType.equals(that.serviceType) : that.serviceType == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (serviceType != null ? serviceType.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return title;
    }
}
