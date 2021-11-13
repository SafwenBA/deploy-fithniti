package com.team.fithniti.demo.dto.request;

import com.team.fithniti.demo.util.ReportedBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class NewReport {
    private UUID reporterId;
    private UUID reportedId;
    private Long rideId;
    private List<Long> reportTypes;
    private String status;
}
