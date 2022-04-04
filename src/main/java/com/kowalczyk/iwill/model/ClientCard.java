package com.kowalczyk.iwill.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clientCard")
public class ClientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String desc;
    @OneToMany(mappedBy = "clientCard", cascade=CascadeType.ALL)
    private List<Visit> visits;
    @OneToOne(mappedBy = "clientCard", cascade=CascadeType.ALL)
    private Client client;

    public ClientCard() {
    }

    public ClientCard(String desc, Client client) {
        this.desc = desc;
//        this.client = client;
    }

    public ClientCard(String desc, Client client, List<Visit> visits) {
        this.desc = desc;
//        this.client = client;
        this.visits = visits;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

//    public Client getClient() {
//        return client;
//    }
//
//    public void setClient(Client client) {
//        this.client = client;
//    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "ClientCard{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
//                ", client=" + client +
                ", visits=" + visits +
                '}';
    }
}
