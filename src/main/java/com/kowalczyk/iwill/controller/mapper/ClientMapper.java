package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ClientDTO;
import com.kowalczyk.iwill.model.Client;

import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.mapper.ClientCardMapper.mapToClientCard;

public class ClientMapper {

    public static List<Client> mapClientDTOToList(List<ClientDTO> clientDTOS) {
        return clientDTOS.stream()
                .map(ClientMapper::mapToClient)
                .collect(Collectors.toList());
    }


    public static Client mapToClient(ClientDTO clientDTO) {
        return Client.builder()
                .id(clientDTO.getId())
                .firstname(clientDTO.getFirstname())
                .lastname(clientDTO.getLastname())
                .desc(clientDTO.getDesc())
                .clientCard(mapToClientCard(clientDTO.getClientCardDTO()))
                .build();
    }
}
