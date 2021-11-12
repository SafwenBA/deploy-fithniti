package com.team.fithniti.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AdminAction {
    private String status ;
    private String message ;
}
