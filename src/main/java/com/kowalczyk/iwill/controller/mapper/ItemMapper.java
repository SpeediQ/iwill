package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ItemDTO;
import com.kowalczyk.iwill.model.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemMapper {

    public static List<Item> mapToItemList(List<ItemDTO> itemDTOS) {
        return itemDTOS.stream()
                .map(ItemMapper::mapToItem)
                .collect(Collectors.toList());
    }

    public static Item mapToItem(ItemDTO itemDTO) {
        return Item.builder()
                .title(itemDTO.getTitle())
                .desc(itemDTO.getDesc())
                .price(itemDTO.getPrice())
                .build();

    }


}
