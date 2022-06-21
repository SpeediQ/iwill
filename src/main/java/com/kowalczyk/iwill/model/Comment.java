package com.kowalczyk.iwill.model;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "DESCRIPTION")
    private String desc;

    private String name;

    private String value;

    private String title;

    @OneToOne
    @JoinColumn(name="CLIENT_SERVICES_ID")
    private ClientServ clientServ;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ITEM_ID")
    private Item item;


    public Comment() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Comment(ClientServ clientServ) {
        this.clientServ = clientServ;
    }

    public Comment(Item item) {
        this.item = item;
    }

    public Comment(ClientServ clientServ, Item item) {
        this.clientServ = clientServ;
        this.item = item;
    }

    public Comment(String desc, String name, String value) {
        this.desc = desc;
        this.name = name;
        this.value = value;
    }

    public Comment(String desc, String name, String value, Item item) {
        this.desc = desc;
        this.name = name;
        this.value = value;
        this.item = item;
    }

    public Comment(String name, String value, ClientServ clientServ) {
        this.name = name;
        this.value = value;
        this.clientServ = clientServ;
    }



    public Comment(String name, String value, ClientServ clientServ, Item item) {
        this.name = name;
        this.value = value;
        this.clientServ = clientServ;
        this.item = item;
    }

    public Comment(String desc) {
        this.desc = desc;
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

    public ClientServ getClientServ() {
        return clientServ;
    }

    public void setClientServ(ClientServ clientServ) {
        this.clientServ = clientServ;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return name;
    }
}
