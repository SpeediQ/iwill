package com.kowalczyk.iwill.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "clientCard")
@Builder
public class ClientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String desc;
    //    @OneToMany(mappedBy = "clientCard", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
//    private List<Visit> visits = new LinkedList<Visit>();
    @OneToOne(mappedBy = "clientCard", cascade = CascadeType.ALL)
    private Client client;

    public ClientCard(long id, String desc, Client client) {
        this.id = id;
        this.desc = desc;
        this.client = client;
    }

    public ClientCard() {
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

    @Override
    public String toString() {
        return "ClientCard{" +
                "id=" + id +
                ", desc='" + desc + '\'' +

                '}';
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClientCard)) return false;

        ClientCard that = (ClientCard) o;

        if (id != that.id) return false;
        if (desc != null ? !desc.equals(that.desc) : that.desc != null) return false;
        return client != null ? client.equals(that.client) : that.client == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (client != null ? client.hashCode() : 0);
        return result;
    }
}
