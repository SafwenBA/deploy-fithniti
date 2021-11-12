package com.team.fithniti.demo.dto.request;

import com.team.fithniti.demo.util.ReportAction;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class ReportHandler {//XD handler
    private String reason;
    private ReportAction reportAction;
    private UUID adminId;
    private Long rideReportId;
}
