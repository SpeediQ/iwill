package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ClientCardDTO;
import com.kowalczyk.iwill.model.ClientCard;

import java.util.List;
import java.util.stream.Collectors;

public class ClientCardMapper {

    public static List<ClientCard> mapToClientCardList(List<ClientCardDTO> clientCardDTOS) {
        return clientCardDTOS.stream()
                .map(ClientCardMapper::mapToClientCard)
                .collect(Collectors.toList());
    }


    public static ClientCard mapToClientCard(ClientCardDTO clientCardDTO) {
        return ClientCard.builder()
                .id(clientCardDTO.getId())
                .desc(clientCardDTO.getDesc())
//                .visits(mapVisitDTOToList(clientCardDTO.getVisitDTOS()))
                .build();
    }
}
