package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.CommentDTO;
import com.kowalczyk.iwill.controller.dto.ItemDTO;
import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.model.Item;

public class ItemMapper {



    public static Item mapToItem(ItemDTO itemDTO){
        return Item.builder()
                .title(itemDTO.getTitle())
                .desc(itemDTO.getDesc())
                .price(itemDTO.getPrice())
                .build();

    }



}
