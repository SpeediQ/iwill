package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ClientCardDTO;
import com.kowalczyk.iwill.model.ClientCard;

import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.mapper.VisitDTOMapper.mapVisitToDTOList;


public class ClientCardDTOMapper {

    public static List<ClientCardDTO> mapClientCardToDTOList(List<ClientCard> clientCards) {
        return clientCards.stream()
                .map(ClientCardDTOMapper::mapClientCardToDTO)
                .collect(Collectors.toList());
    }


    public static ClientCardDTO mapClientCardToDTO(ClientCard clientCard) {
        return ClientCardDTO.builder()
                .id(clientCard.getId())
                .desc(clientCard.getDesc())
                .visitDTOS(mapVisitToDTOList(clientCard.getVisits()))
                .build();
    }


}
