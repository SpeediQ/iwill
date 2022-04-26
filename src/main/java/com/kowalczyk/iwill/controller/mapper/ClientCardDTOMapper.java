package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ClientCardDTO;
import com.kowalczyk.iwill.model.ClientCard;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class ClientCardDTOMapper {

    public static List<ClientCardDTO> mapToClientCardDTOList(List<ClientCard> clientCards) {
        return clientCards.stream()
                .map(ClientCardDTOMapper::mapToClientCardDTO)
                .collect(Collectors.toList());
    }


    public static ClientCardDTO mapToClientCardDTO(ClientCard clientCard) {
        return ClientCardDTO.builder()
                .id(clientCard.getId())
                .desc(clientCard.getDesc())
                .build();
    }


}
