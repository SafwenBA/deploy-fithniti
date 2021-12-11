package com.team.fithniti.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateResponse {
    private String status ;
    private String message ;
}
