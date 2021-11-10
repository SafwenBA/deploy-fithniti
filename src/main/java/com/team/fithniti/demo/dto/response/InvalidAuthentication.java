package com.team.fithniti.demo.dto.response;

import lombok.Data;

@Data
public class InvalidAuthentication extends AuthenticationResponse{
    private String status ;
    private String errorMessage ;

    public InvalidAuthentication(String status, String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
    }
}
