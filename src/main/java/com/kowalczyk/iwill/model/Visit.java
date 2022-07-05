package com.kowalczyk.iwill.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Visit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DESCRIPTION")
    private String desc;

    @OneToMany(mappedBy = "visit")
    private Set<ClientServ> clientServSet = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_CARD_ID")
    private ClientCard clientCard;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private String time;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "STATUS_ID")
    private Status status;

    private String title;

    public Visit() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Visit(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<ClientServ> getClientServSet() {
        return clientServSet;
    }

    public void setClientServSet(Set<ClientServ> clientServSet) {
        this.clientServSet = clientServSet;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", clientServSet=" + clientServSet +
                '}';
    }

    public String shortName() {
        return "Wizyta: " + this.getDesc();
    }

    public Double getSum() {
        double sum = 0;
        if (getClientServSet() != null && getClientServSet().size() > 0){
            for (ClientServ clientServ : getClientServSet()) {
                sum += clientServ.getPrice();
            }
        }
        return sum;
    }
}
