package com.kowalczyk.iwill.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "services")

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

    public ClientServ(long id, String desc, Comment comment, Visit visit) {
        this.id = id;
        this.desc = desc;
        this.comment = comment;
        this.visit = visit;
    }

    public ClientServ() {
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

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    @Override
    public String toString() {
        return "ClientServ{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", visit=" + visit +
                '}';
    }
}
