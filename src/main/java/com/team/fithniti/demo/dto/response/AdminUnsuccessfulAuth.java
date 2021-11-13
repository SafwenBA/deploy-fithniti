package com.team.fithniti.demo.dto.response;

import lombok.Data;

@Data
public class AdminUnsuccessfulAuth extends AdminAuthResponse{
    private String status ;
    private String errorMessage ;

    public AdminUnsuccessfulAuth(String status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
