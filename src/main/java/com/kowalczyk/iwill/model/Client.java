package com.kowalczyk.iwill.model;

import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstname;
    private String lastname;
    private String desc;
    @OneToOne()
    @JoinColumn(name = "CLIENT_CARD_ID")
    private ClientCard clientCard;

    public Client() {
    }

    public Client(long id, String firstname, String lastname, String desc, ClientCard clientCard) {
        this.id = id;
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

    public void addDefaultClientCardToClient() {
        if (this.getClientCard() == null) {
            ClientCard clientCard = new ClientCard();
            clientCard.setClient(this);
            this.setClientCard(clientCard);
        }
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", desc='" + desc + '\'' +

                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;

        Client client = (Client) o;

        if (id != client.id) return false;
        if (firstname != null ? !firstname.equals(client.firstname) : client.firstname != null) return false;
        if (lastname != null ? !lastname.equals(client.lastname) : client.lastname != null) return false;
        if (desc != null ? !desc.equals(client.desc) : client.desc != null) return false;
        return clientCard != null ? clientCard.equals(client.clientCard) : client.clientCard == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (desc != null ? desc.hashCode() : 0);
        result = 31 * result + (clientCard != null ? clientCard.hashCode() : 0);
        return result;
    }

    public String getFullName() {
        String fullName = "";
        if (this.firstname != null) {
            fullName = this.firstname;
        }
        if (this.getLastname() != null) {
            fullName = fullName + " " + this.getLastname();
        }
        return fullName;
    }
}
