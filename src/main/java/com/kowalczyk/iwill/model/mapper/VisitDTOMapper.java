package com.kowalczyk.iwill.model.mapper;

import com.kowalczyk.iwill.model.Visit;
import com.kowalczyk.iwill.model.dto.VisitDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

import static com.kowalczyk.iwill.model.mapper.ClientServDTOMapper.mapToClientServDTOList;

@Transactional
public class VisitDTOMapper {
    //    private int id;
//    private String desc;
//    private Set<ClientServDTO> clientServSet = new HashSet<>();
//    private Date date;
//    private String time;
//    private Status status;
//    private String title;
    public static VisitDTO mapToVisitDTO(Visit visit) {

        VisitDTO visitDTO = VisitDTO.builder()
                .id(visit.getId())
                .desc(visit.getDesc())
                .date(visit.getDate())
                .time(visit.getTime())
                .title(visit.getTitle())
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

    public static Set<VisitDTO> mapToVisitDTOList(Set<Visit> visits) {
        return visits.stream()
                .map(VisitDTOMapper::mapToVisitDTO)
                .collect(Collectors.toSet());
    }

}
