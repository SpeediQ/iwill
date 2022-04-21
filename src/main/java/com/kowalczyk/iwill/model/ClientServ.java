package com.kowalczyk.iwill.model;

import lombok.Builder;
import lombok.Data;
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
    @JoinColumn(name = "COMMENT_ID")
    private Comment comment;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "VISIT_ID")
    private Visit visit;

    @Tolerate
    public ClientServ() {
    }
}
