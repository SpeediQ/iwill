package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ClientDTO;
import com.kowalczyk.iwill.model.Client;

import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.mapper.ClientCardDTOMapper.mapClientCardToDTO;


public class ClientDTOMapper {

    public static List<ClientDTO> mapClientToDTOList(List<Client> clients) {
        return clients.stream()
                .map(ClientDTOMapper::mapClientToDTO)
                .collect(Collectors.toList());
    }


    public static ClientDTO mapClientToDTO(Client client) {
        return ClientDTO.builder()
                .id(client.getId())
                .firstname(client.getFirstname())
                .lastname(client.getLastname())
                .desc(client.getDesc())
                .clientCardDTO(mapClientCardToDTO(client.getClientCard()))
                .build();
    }


}
