package com.kowalczyk.iwill.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
public class ClientCardDTO {

    private int id;
    private Set<VisitDTO> visitSet = new HashSet<>();


    public ClientCardDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Set<VisitDTO> getVisitSet() {
        return visitSet;
    }

    public void setVisitSet(Set<VisitDTO> visitSet) {
        this.visitSet = visitSet;
    }

    @Override
    public String toString() {
        return "ClientCardDTO{" +
                "id=" + id +
                '}';
    }
}
