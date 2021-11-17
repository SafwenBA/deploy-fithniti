package com.team.fithniti.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ReportSubmitted {
    private String status;
    private String message;
}
