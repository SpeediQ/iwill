package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.ClientServDTO;
import com.kowalczyk.iwill.model.ClientServ;

import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.mapper.CommentMapper.mapToComment;

public class ClientServMapper {

    public static List<ClientServ> mapToClientServList(List<ClientServDTO> clientServDTOS) {
        return clientServDTOS.stream()
                .map(ClientServMapper::mapToClientServ)
                .collect(Collectors.toList());
    }

    public static ClientServ mapToClientServ(ClientServDTO clientServDTO) {
        return ClientServ.builder()
                .id(clientServDTO.getId())
                .desc(clientServDTO.getDesc())
                .comment(mapToComment(clientServDTO.getCommentDTO()))
                .build();
    }
}
