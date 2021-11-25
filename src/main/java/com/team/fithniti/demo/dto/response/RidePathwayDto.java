package com.team.fithniti.demo.dto.response;

import com.team.fithniti.demo.model.RidePathway;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RidePathwayDto {
    private String from;
    private String to ;

    public static RidePathwayDto fromEntity(RidePathway pathway){
        assert pathway != null;
        String from = pathway.getFromCountry().getName()+"," + pathway.getFromGovernorate().getName()+"," + pathway.getFromCity().getName();
        String to = pathway.getToCountry().getName()+"," + pathway.getToGovernorate().getName()+"," + pathway.getToCity().getName();
        return RidePathwayDto.builder()
                .from(from)
                .to(to)
                .build();
    }
}
