package com.kowalczyk.iwill.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ServiceType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DESCRIPTION")
    private String desc;

    private String name;

    private double value;

    @OneToOne
    @JoinColumn(name = "STATUS_ID")
    private Status status;


    public ServiceType() {
    }

    public ServiceType(Status status) {
        this.status = status;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ServiceType{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceType)) return false;

        ServiceType that = (ServiceType) o;

        if (id != that.id) return false;
        if (Double.compare(that.value, value) != 0) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public String niceString() {
        return name + " ; Cena: " + value + " ; Opis: " + desc;
    }
    public String niceChooseString() {
        return "Wybrano: " + name + " ; Cena: " + value + " ; Opis: " + desc;
    }
}
