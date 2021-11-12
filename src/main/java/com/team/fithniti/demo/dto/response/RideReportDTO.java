package com.team.fithniti.demo.dto.response;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.model.Passenger;
import com.team.fithniti.demo.model.ReportType;
import com.team.fithniti.demo.model.RideReport;
import com.team.fithniti.demo.util.ReportedBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideReportDTO {
    private Long id;
    private ReportedBy reportedBy;
    private List<ReportType> reportTypes;
    private RideDTO rideDTO;
    private Passenger passenger;

    public static RideReportDTO fromEntity(RideReport rideReport){
        if(rideReport == null){
            throw new InvalidResource(null, "", "");//TODO: write something over here
        }
        return RideReportDTO.builder()
                .id(rideReport.getId())
                .reportedBy(rideReport.getReportedBy())
                .passenger(rideReport.getPassenger())
                .rideDTO( RideDTO.fromEntity(rideReport.getRide()))
                .reportTypes( (rideReport.getReportTypes() == null) ? new ArrayList<>() :
                        new ArrayList<>(rideReport.getReportTypes()))
                .build();
    }
}
