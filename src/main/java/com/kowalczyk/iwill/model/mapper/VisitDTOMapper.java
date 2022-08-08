package com.kowalczyk.iwill.model.mapper;

import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.model.dto.VisitDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.model.mapper.ClientServDTOMapper.mapToClientServDTOList;

@Transactional
public class VisitDTOMapper {
    public static VisitDTO mapToVisitDTO(Visit visit) {

        VisitDTO visitDTO = VisitDTO.builder()
                .id(visit.getId())
                .desc(visit.getDesc())
                .date(visit.getDate())
                .time(visit.getTime())
                .title(visit.getTitle())
                .promotion(visit.getPromotion())
                .build();
        if (visit.getClientServSet() != null) {
            visitDTO.setClientServSet(mapToClientServDTOList(visit.getClientServSet()));
        }

        if (visit.getStatus() != null) {
            visitDTO.setStatus(visit.getStatus());
        }

        if (visit.getCode() != null) {
            visitDTO.setCode(visit.getCode());
        }
        return visitDTO;
    }

    public static Set<VisitDTO> mapToVisitDTOSet(Set<Visit> visits) {
        return visits.stream()
                .map(VisitDTOMapper::mapToVisitDTO)
                .collect(Collectors.toSet());
    }

    public static List<VisitDTO> mapToVisitDTOList(List<Visit> visits) {
        return visits.stream()
                .map(VisitDTOMapper::mapToVisitDTO)
                .collect(Collectors.toList());
    }

}
