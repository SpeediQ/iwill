package com.kowalczyk.iwill.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @Override
    public String toString() {
        return "ClientCard "+ id;
    }

}
