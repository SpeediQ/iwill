package com.kowalczyk.iwill.model.mapper;

import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.dto.ClientCardDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.model.mapper.VisitDTOMapper.mapToVisitDTOSet;

@Transactional
public class ClientCardDTOMapper {

    public static ClientCardDTO mapToClientCardDTO(ClientCard clientCard) {
        ClientCardDTO clientCardDTO = ClientCardDTO.builder()
                .id(clientCard.getId())
                .build();
        if (clientCard.getVisitSet() != null) {
            clientCardDTO.setVisitSet(mapToVisitDTOSet(clientCard.getVisitSet()));
        }
        return clientCardDTO;
    }

    public static Set<ClientCardDTO> mapToClientCardDTOList(Set<ClientCard> clientCards) {
        return clientCards.stream()
                .map(ClientCardDTOMapper::mapToClientCardDTO)
                .collect(Collectors.toSet());
    }

}
