package com.kowalczyk.iwill.model;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Builder
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String desc;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "ITEM_ID")
    private Item item;

    @Tolerate
    public Comment() {
    }
}
