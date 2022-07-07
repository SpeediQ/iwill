package com.kowalczyk.iwill.model.mapper;

import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.dto.ClientServDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Transactional
public class ClientServDTOMapper {
//    private int id;
//    private String desc;
//    private String title;
//    private double price;
    public static ClientServDTO mapToClientServDTO(ClientServ clientServ){
        ClientServDTO clientServDTO = ClientServDTO.builder()
                .id(clientServ.getId())
                .desc(clientServ.getDesc())
                .title(clientServ.getTitle())
                .price(clientServ.getPrice())
                .build();
        if (clientServ.getServiceType() != null){
            clientServDTO.setServiceType(clientServ.getServiceType());
        }
        return clientServDTO;
    }

    public static Set<ClientServDTO> mapToClientServDTOList(Set<ClientServ> clientServs) {
        return clientServs.stream()
                .map(ClientServDTOMapper::mapToClientServDTO)
                .collect(Collectors.toSet());
    }

}
