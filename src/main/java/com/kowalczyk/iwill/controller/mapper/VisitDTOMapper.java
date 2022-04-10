package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.VisitDTO;
import com.kowalczyk.iwill.model.Visit;

import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.mapper.ClientServDTOMapper.mapClientServToDTOList;


public class VisitDTOMapper {

    public static List<VisitDTO> mapVisitToDTOList(List<Visit> visits) {
        return visits.stream()
                .map(visit -> mapVisitToDTO(visit))
                .collect(Collectors.toList());
    }


    public static VisitDTO mapVisitToDTO(Visit visit) {
        return VisitDTO.builder()
                .id(visit.getId())
                .desc(visit.getDesc())
                .clientServDTOS(mapClientServToDTOList(visit.getClientServs()))
                .build();
    }


}
