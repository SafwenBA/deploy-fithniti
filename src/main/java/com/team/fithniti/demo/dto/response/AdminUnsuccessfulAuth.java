package com.team.fithniti.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminUnsuccessfulAuth extends AdminAuthResponse{
    private String error ;

    public AdminUnsuccessfulAuth(String status, String errorMessage) {
        this.error = errorMessage;
    }
}
