package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ItemDTO;
import com.kowalczyk.iwill.model.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemDTOMapper {

    public static List<ItemDTO> mapItemToDTOList(List<Item> items){
        return items.stream()
                .map(item -> mapToItemDTO(item))
                .collect(Collectors.toList());
    }

    public static ItemDTO mapToItemDTO(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .desc(item.getDesc())
                .title(item.getTitle())
                .price(item.getPrice())
                .build();
    }
}
