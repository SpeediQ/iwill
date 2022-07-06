package com.kowalczyk.iwill.model.dto;

import com.kowalczyk.iwill.model.ServiceType;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ClientServDTO {

    private int id;
    private String desc;
    private String title;
    private double price;
    private ServiceType serviceType;


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

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public String toString() {
        return "ClientServDTO{" +
                "desc='" + desc + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
