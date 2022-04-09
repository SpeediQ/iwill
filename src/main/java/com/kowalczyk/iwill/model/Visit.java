package com.kowalczyk.iwill.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "visits")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String desc;
    @ManyToOne(fetch = FetchType.LAZY)
    private ClientCard clientCard;
    @OneToMany(mappedBy = "visit")
    private List<ClientServ> clientServs;

    public Visit() {
    }

    public Visit(String desc) {
        this.desc = desc;
    }

    public Visit(String desc, ClientCard clientCard, List<ClientServ> clientServs) {
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
}
