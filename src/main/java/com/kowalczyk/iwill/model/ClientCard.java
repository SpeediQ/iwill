package com.kowalczyk.iwill.model;

import javax.persistence.*;

@Entity
public class ClientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name="CLIENT_ID")
    private Client client;

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

    @Override
    public String toString() {
        return "ClientCard{" +
                "id=" + id +
                '}';
    }
}
