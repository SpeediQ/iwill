package com.kowalczyk.iwill.model;

import javax.persistence.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class ClientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name="CLIENT_ID")
    private Client client;

    @OneToMany(mappedBy = "clientCard")
    private Set<Visit> visitSet = new HashSet<>();

    public ClientCard() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Visit> getVisitSet() {
        return visitSet;
    }

    public void setVisitSet(Set<Visit> visitSet) {
        this.visitSet = visitSet;
    }

    public List<Visit> getSortedVisitListByVisitSet() {
        return getVisitSet().stream()
                .sorted(Comparator
                        .comparing(Visit::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "ClientCard "+ id;
    }

}
