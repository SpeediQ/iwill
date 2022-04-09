package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ClientServDTO;
import com.kowalczyk.iwill.model.ClientServ;

import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.mapper.CommentDTOMapper.mapToCommentDTO;
import static com.kowalczyk.iwill.controller.mapper.ItemDTOMapper.mapToItemDTO;


public class ClientServDTOMapper {

    public static List<ClientServDTO> mapClientServToDTOList(List<ClientServ> clientServs) {
        return clientServs.stream()
                .map(ClientServDTOMapper::mapClientServToDTO)
                .collect(Collectors.toList());
    }


    public static ClientServDTO mapClientServToDTO(ClientServ clientServ) {
        return ClientServDTO.builder()
                .id(clientServ.getId())
                .desc(clientServ.getDesc())
                .comment(mapToCommentDTO(clientServ.getComment()))
                .build();
    }


}
