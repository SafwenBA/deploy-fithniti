package com.team.fithniti.demo.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthenticationResponse {
    private final String token;
    private String username;

    public AuthenticationResponse(String token, String username) {

        this.token = token;
        this.username = username;
    }
    public AuthenticationResponse(String token) {
        this.token = token;

    }
}
