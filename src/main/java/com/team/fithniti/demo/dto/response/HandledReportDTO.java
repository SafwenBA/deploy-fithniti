package com.team.fithniti.demo.dto.response;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.model.HandledReport;
import com.team.fithniti.demo.model.RideReport;
import com.team.fithniti.demo.util.ReportAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HandledReportDTO {
    private Long id;
    private String reason;
    private ReportAction action;
    private AppUser admin;
    private RideReport rideReport;

    public static HandledReportDTO formEntity(HandledReport handledReport){
        if(handledReport == null )
            throw new InvalidResource(null, "", "");//TODO: WRITE SOME FUCKING CODE YOU BASTARD
        return HandledReportDTO.builder()
                .id(handledReport.getId())
                .reason(handledReport.getReason())
                .action(handledReport.getAction())
//                .admin(handledReport.getAdmin())
                .rideReport(handledReport.getRideReport())
                .build();
    }
}
