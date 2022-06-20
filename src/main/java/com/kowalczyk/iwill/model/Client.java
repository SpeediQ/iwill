package com.kowalczyk.iwill.model;

import javax.persistence.*;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String lastname;
    private String comment;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.ALL)
    private ClientCard clientCard = new ClientCard();

    public Client() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientCard getClientCard() {
        return clientCard;
    }

    public void setClientCard(ClientCard clientCard) {
        this.clientCard = clientCard;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", comment='" + comment + '\'' +
                ", clientCard=" + clientCard +
                '}';
    }
}
