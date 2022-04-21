package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.VisitDTO;
import com.kowalczyk.iwill.model.Visit;

import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.mapper.ClientServDTOMapper.mapToClientServDTOList;


public class VisitDTOMapper {

    public static List<VisitDTO> mapToVisitDTOList(List<Visit> visits) {
        return visits.stream()
                .map(VisitDTOMapper::mapToVisitDTO)
                .collect(Collectors.toList());
    }


    public static VisitDTO mapToVisitDTO(Visit visit) {
        return VisitDTO.builder()
                .id(visit.getId())
                .desc(visit.getDesc())
                .clientServDTOS(mapToClientServDTOList(visit.getClientServs()))
                .build();
    }


}
