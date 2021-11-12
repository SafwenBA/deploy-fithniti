package com.team.fithniti.demo.dto.request;

import com.team.fithniti.demo.model.ReportType;
import com.team.fithniti.demo.util.ReportedBy;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class NewReport {
    private ReportedBy reportedBy;
    private List<ReportType> reportTypes;
    private Long rideId;
    private Long passengerId;
}
