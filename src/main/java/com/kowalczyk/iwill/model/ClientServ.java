package com.kowalczyk.iwill.model;

import javax.persistence.*;

@Entity
@Table(name = "services")
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

    public ClientServ() {
    }

    public ClientServ(String desc, Item item, Comment comment) {
        this.desc = desc;
        this.item = item;
        this.comment = comment;
    }

    public ClientServ(String desc, Item item, Comment comment, Visit visit) {
        this.desc = desc;
        this.item = item;
        this.comment = comment;
        this.visit = visit;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
                ", item=" + item +
                ", comment=" + comment +
                ", visit=" + visit +
                '}';
    }


    public static final class ClientServBuilder {
        private ClientServ clientServ;

        private ClientServBuilder() {
            clientServ = new ClientServ();
        }

        public static ClientServBuilder aClientServ() {
            return new ClientServBuilder();
        }

        public ClientServBuilder withId(long id) {
            clientServ.setId(id);
            return this;
        }

        public ClientServBuilder withDesc(String desc) {
            clientServ.setDesc(desc);
            return this;
        }

        public ClientServBuilder withItem(Item item) {
            clientServ.setItem(item);
            return this;
        }

        public ClientServBuilder withComment(Comment comment) {
            clientServ.setComment(comment);
            return this;
        }

        public ClientServBuilder withVisit(Visit visit) {
            clientServ.setVisit(visit);
            return this;
        }

        public ClientServ build() {
            return clientServ;
        }
    }
}
