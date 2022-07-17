package com.kowalczyk.iwill.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String lastname;
    private String comment;
    private String code;
    @OneToMany(mappedBy = "client")
    private Set<ContactAddress> contactAddresses = new HashSet<>();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "client", cascade = CascadeType.ALL)
    private ClientCard clientCard;

    public Client() {
    }

    public Client(ClientCard clientCard) {
        this.clientCard = clientCard;
    }

    public Client(String comment) {
        this.comment = comment;
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

    public String getNiceName() {
        return name + " " + lastname;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<ContactAddress> getContactAddresses() {
        return contactAddresses;
    }

    public void setContactAddresses(Set<ContactAddress> contactAddresses) {
        this.contactAddresses = contactAddresses;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +

                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;

        Client client = (Client) o;

        if (id != client.id) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (lastname != null ? !lastname.equals(client.lastname) : client.lastname != null) return false;
        if (comment != null ? !comment.equals(client.comment) : client.comment != null) return false;
        if (code != null ? !code.equals(client.code) : client.code != null) return false;
        if (date != null ? !date.equals(client.date) : client.date != null) return false;
        return clientCard != null ? clientCard.equals(client.clientCard) : client.clientCard == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (clientCard != null ? clientCard.hashCode() : 0);
        return result;
    }

    public Boolean isIdValid(){
        return getId() > 0 ? true : false;
    }
}
