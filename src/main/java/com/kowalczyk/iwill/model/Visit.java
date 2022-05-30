package com.kowalczyk.iwill.model;

import lombok.Builder;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "visits")
@Builder

public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String desc;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENTCARD_ID")
    private ClientCard clientCard;
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "visit",
            cascade = CascadeType.MERGE)
    private List<ClientServ> clientServs = new LinkedList<ClientServ>();

    public Visit() {
    }

    public Visit(long id, String desc, ClientCard clientCard, List<ClientServ> clientServs) {
        this.id = id;
        this.desc = desc;
        this.clientCard = clientCard;
        this.clientServs = clientServs;
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

    public ClientCard getClientCard() {
        return clientCard;
    }

    public void setClientCard(ClientCard clientCard) {
        this.clientCard = clientCard;
    }

    public List<ClientServ> getClientServs() {
        return clientServs;
    }

    public void setClientServs(List<ClientServ> clientServs) {
        this.clientServs = clientServs;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", clientCard=" + clientCard +
                ", clientServs=" + clientServs +
                '}';
    }

    public String getFullName() {
        String fullName = "";
        if (this.desc != null) {
            fullName = this.desc;
        }

        return "dodać tytuł " + fullName;
    }

}
