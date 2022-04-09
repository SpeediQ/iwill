package com.kowalczyk.iwill.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "services")
@Data
@Builder
public class ClientServ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String desc;
    @OneToOne
    @JoinColumn(name = "ITEM_ID")
    private Item item;
    @OneToOne
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;
    @ManyToOne(fetch = FetchType.LAZY)
    private Visit visit;

    @Tolerate
    public ClientServ() {
    }
}
