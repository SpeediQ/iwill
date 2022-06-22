package com.kowalczyk.iwill.model.mapper;

import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.dto.ClientCardDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
public class ClientCardDTOMapper {

    public static ClientCardDTO mapToClientCardDTO(ClientCard clientCard){
        ClientCardDTO clientCardDTO = ClientCardDTO.builder()
                .id(clientCard.getId())
                .build();
        if (clientCard.getVisitSet() != null){
            clientCardDTO.setVisitSet(clientCard.getVisitSet());
        }
        return clientCardDTO;
    }

    public static List<ClientCardDTO> mapToClientCardDTOList(List<ClientCard> clientCards) {
        return clientCards.stream()
                .map(ClientCardDTOMapper::mapToClientCardDTO)
                .collect(Collectors.toList());
    }

}
