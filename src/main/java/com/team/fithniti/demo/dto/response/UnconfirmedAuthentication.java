package com.team.fithniti.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UnconfirmedAuthentication extends AuthenticationResponse{
    private String status ;
    private UUID userId ;
    private String message ;
}
