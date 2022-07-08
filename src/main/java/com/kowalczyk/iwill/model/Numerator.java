package com.kowalczyk.iwill.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Numerator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

    private int value;
    private String symbol;


    public Numerator() {
    }

    public Numerator(String name, String description, int value, String symbol) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.symbol = symbol;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Numerator)) return false;

        Numerator numerator = (Numerator) o;

        if (id != numerator.id) return false;
        if (value != numerator.value) return false;
        if (name != null ? !name.equals(numerator.name) : numerator.name != null) return false;
        if (description != null ? !description.equals(numerator.description) : numerator.description != null)
            return false;
        return symbol != null ? symbol.equals(numerator.symbol) : numerator.symbol == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + value;
        result = 31 * result + (symbol != null ? symbol.hashCode() : 0);
        return result;
    }
}
