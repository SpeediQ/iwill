package com.kowalczyk.iwill.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class ClientServ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "DESCRIPTION")
    private String desc;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "VISIT_ID")
    private Visit visit;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "clientServ", cascade = CascadeType.ALL)
    private Comment comment;

    private String title;



    public ClientServ() {
    }


    public ClientServ(Comment comment) {
        this.comment = comment;
    }

    public ClientServ(Visit visit) {
        this.visit = visit;
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

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return desc;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public void addComment(String name, String value, Item item) {
        setComment(new Comment(name, value, this, item));
    }

}
