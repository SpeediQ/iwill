package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ClientCardDTO;
import com.kowalczyk.iwill.controller.dto.ClientServDTO;
import com.kowalczyk.iwill.controller.dto.VisitDTO;
import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Visit;

import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.mapper.ClientServMapper.mapClientServDTOToList;
import static com.kowalczyk.iwill.controller.mapper.VisitMapper.mapVisitDTOToList;

public class ClientCardMapper {
//
//    public static List<ClientServ> mapClientServDTOToList(List<ClientServDTO> clientServDTOS) {
//        return clientServDTOS.stream()
//                .map(ClientServMapper::mapToClientServ)
//                .collect(Collectors.toList());
//    }


    public static ClientCard mapToClientCard(ClientCardDTO clientCardDTO) {
        return ClientCard.builder()
                .id(clientCardDTO.getId())
                .desc(clientCardDTO.getDesc())
                .visits(mapVisitDTOToList(clientCardDTO.getVisits()))
                .build();
    }
}
