package com.kowalczyk.iwill.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "items")
@Builder
@Data
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String desc;
    private Double price;

    @Tolerate
    public Item() {
    }

    public void updateItem(Item source) {
        if (source.getTitle() != null) {
            title = source.getTitle();
        }
        if (source.getDesc() != null) {
            desc = source.getDesc();
        }
        if (source.getPrice() != null) {
            price = source.getPrice();
        }
    }


}
