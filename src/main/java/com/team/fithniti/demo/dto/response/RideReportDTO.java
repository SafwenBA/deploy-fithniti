package com.team.fithniti.demo.dto.response;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.model.ReportType;
import com.team.fithniti.demo.model.RideReport;
import com.team.fithniti.demo.util.ReportStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideReportDTO {
    private Long id;
    private List<ReportType> reportTypes;
    private RideDTO rideDTO;
    private AppUser reported;
    private AppUser reporter;
    private ReportStatus reportStatus;

    public static RideReportDTO fromEntity(RideReport rideReport){
        if(rideReport == null){
            throw new InvalidResource(null, "", "");//TODO: write something over here
        }
        return RideReportDTO.builder()
                .id(rideReport.getId())
                .rideDTO(RideDTO.fromEntity(rideReport.getRide()))
                .reported(rideReport.getReported())
                .reporter(rideReport.getReporter())
                .reportStatus(rideReport.getReportStatus())
                .reportTypes((rideReport.getReportTypes() == null) ? new ArrayList<>() :
                        rideReport.getReportTypes())
                .build();
    }
}
