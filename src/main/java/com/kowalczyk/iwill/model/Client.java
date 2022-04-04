package com.kowalczyk.iwill.model;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String lastname;
    private String desc;
    @OneToOne()
//    @JoinColumn(name="CLIENT_CARD_ID")
    @JoinColumn(name = "CLIENT_CARD_ID")
    private ClientCard clientCard;

    public Client() {
    }

    public Client(String firstname, String lastname, String desc) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.desc = desc;
    }

    public Client(String firstname, String lastname, String desc, ClientCard clientCard) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.desc = desc;
        this.clientCard = clientCard;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", desc='" + desc + '\'' +
                ", clientCard=" + clientCard +
                '}';
    }
}
