package com.kowalczyk.iwill.model;

import com.kowalczyk.iwill.model.Item;

import javax.persistence.*;

@Entity
@Table(name = "coments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String desc;
    @OneToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="ITEM_ID")
    private Item item;

    public Comment() {
    }

    public Comment(String desc, Item item) {
        this.desc = desc;
        this.item = item;
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

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", desc='" + desc + '\'' +
                ", item=" + item +
                '}';
    }

    public Comment createExampleComment (Comment comment, Item entity){
        Comment body = new Comment();
        body.setDesc("examleComment");
        body.setItem(entity);

        return body;
    }
    public Comment createExampleComment (Comment comment){
        Comment body = new Comment();
        Item item = new Item("Title item example", "item Desc example", 562D);
        body.setDesc("examleComment");
        body.setItem(item);

        return body;
    }


}
