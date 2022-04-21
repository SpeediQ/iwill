package com.kowalczyk.iwill.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "visits")
@Builder
@Data
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String desc;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CLIENTCARD_ID")
    private ClientCard clientCard;
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "visit",
            cascade = CascadeType.MERGE)
    private List<ClientServ> clientServs = new LinkedList<ClientServ>();

    @Tolerate
    public Visit() {
    }
}
