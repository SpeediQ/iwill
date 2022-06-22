package com.kowalczyk.iwill.model.mapper;

import com.kowalczyk.iwill.model.Client;
import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.dto.ClientDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.model.mapper.ClientCardDTOMapper.mapToClientCardDTO;

@Transactional
public class ClientDTOMapper {

//    private int id;
//    private String name;
//    private String lastname;
//    private String comment;
//    private ClientCardDTO clientCardDTO;


    public static ClientDTO mapToClientDTO(Client client) {
        ClientDTO clientDTO = ClientDTO.builder()
                .id(client.getId())
                .name(client.getName())
                .lastname(client.getLastname())
                .comment(client.getComment())
                .clientCardDTO(client.getClientCard() != null ? mapToClientCardDTO(client.getClientCard()) :  mapToClientCardDTO(new ClientCard()))
                .build();


        return clientDTO;
    }

    public static List<ClientDTO> mapToClientDTOList(List<Client> clients) {
        return clients.stream()
                .map(client -> mapToClientDTO(client))
                .collect(Collectors.toList());
    }

}
