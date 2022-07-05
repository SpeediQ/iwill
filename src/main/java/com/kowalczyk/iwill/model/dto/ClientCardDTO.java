package com.kowalczyk.iwill.model.dto;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.Visit;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
public class ClientCardDTO {

    private int id;
    private Set<Visit> visitSet = new HashSet<>();


    public ClientCardDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<Visit> getVisitSet() {
        return visitSet;
    }

    public void setVisitSet(Set<Visit> visitSet) {
        this.visitSet = visitSet;
    }

    @Override
    public String toString() {
        return "ClientCardDTO{" +
                "id=" + id +
                '}';
    }
}
