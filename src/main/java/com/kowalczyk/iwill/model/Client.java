package com.kowalczyk.iwill.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "clients")
@Builder
@Data
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

    @Tolerate
    public Client() {
    }

}
