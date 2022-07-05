package com.kowalczyk.iwill.model.mapper;

import com.kowalczyk.iwill.model.ClientCard;
import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.model.dto.ClientCardDTO;
import com.kowalczyk.iwill.model.dto.ClientServDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        if (clientServ.getItem() != null){
            clientServDTO.setItem(clientServ.getItem());
        }
        return clientServDTO;
    }

    public static Set<ClientServDTO> mapToClientServDTOList(Set<ClientServ> clientServs) {
        return clientServs.stream()
                .map(ClientServDTOMapper::mapToClientServDTO)
                .collect(Collectors.toSet());
    }

}
