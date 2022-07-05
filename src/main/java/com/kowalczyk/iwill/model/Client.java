package com.kowalczyk.iwill.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String lastname;
    private String comment;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.ALL)
    private ClientCard clientCard;

    public Client() {
    }

    public Client(ClientCard clientCard) {
        this.clientCard = clientCard;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNiceName(){
        return name + " " + lastname;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +

                '}';
    }
}
