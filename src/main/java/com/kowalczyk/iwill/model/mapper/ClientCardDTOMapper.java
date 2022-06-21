package com.kowalczyk.iwill.model.mapper;

import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.dto.ClientCardDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class ClientCardDTOMapper {

    public static ClientCardDTO mapToClientCardDTO(ClientCard clientCard){
        return ClientCardDTO.builder()
                .id(clientCard.getId())
                .visitSet(clientCard.getVisitSet())
                .build();
    }

}
