package com.kowalczyk.iwill.controller.mapper;

import com.kowalczyk.iwill.controller.dto.VisitDTO;
import com.kowalczyk.iwill.model.Visit;

import java.util.List;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.controller.mapper.ClientServMapper.mapClientServDTOToList;

public class VisitMapper {

    public static List<Visit> mapVisitDTOToList(List<VisitDTO> visitDTOS) {
        return visitDTOS.stream()
                .map(VisitMapper::mapToVisit)
                .collect(Collectors.toList());
    }

    public static Visit mapToVisit(VisitDTO visit) {
        return Visit.builder()
                .id(visit.getId())
                .desc(visit.getDesc())
                .clientServs(mapClientServDTOToList(visit.getClientServDTOS()))
                .build();
    }
}
