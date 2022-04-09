package com.kowalczyk.iwill.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clientCard")
@Builder
@Data
public class ClientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String desc;
    @OneToMany(mappedBy = "clientCard", cascade=CascadeType.ALL)
    private List<Visit> visits;
    @OneToOne(mappedBy = "clientCard", cascade=CascadeType.ALL)
    private Client client;

    @Tolerate
    public ClientCard() {
    }
}
