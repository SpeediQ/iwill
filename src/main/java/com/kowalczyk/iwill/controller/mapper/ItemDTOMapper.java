package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ItemDTO;
import com.kowalczyk.iwill.model.Item;

import java.util.List;
import java.util.stream.Collectors;

public class ItemDTOMapper {

    public static List<ItemDTO> mapToItemDTOList(List<Item> items) {
        return items.stream()
                .map(ItemDTOMapper::mapToItemDTO)
                .collect(Collectors.toList());
    }

    public static ItemDTO mapToItemDTO(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .desc(item.getDesc())
                .title(item.getTitle())
                .price(item.getPrice())
                .type("DTO")
                .build();
    }
}
