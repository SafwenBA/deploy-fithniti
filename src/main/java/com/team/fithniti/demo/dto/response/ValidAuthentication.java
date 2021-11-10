package com.team.fithniti.demo.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class ValidAuthentication extends AuthenticationResponse{
    private  String status ;
    private UUID userId;
    private  String access_token;
    private  String refreshToken ;
    private  String encodedLogo ;
    public ValidAuthentication(String status, UUID userId, String access_token, String refreshToken, String encodedLogo) {
        this.status = status;
        this.userId = userId;
        this.access_token = access_token;
        this.refreshToken = refreshToken;
        this.encodedLogo = encodedLogo;
    }
}
