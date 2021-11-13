package com.team.fithniti.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class ReportHandler {//XD handler
    private String reason;
    private String reportAction;
    private UUID adminId;
    private Long rideReportId;
}
