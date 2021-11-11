package com.team.fithniti.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ValidAuthentication extends AuthenticationResponse{
    private  String status ;
    private UUID userId;
    private  String access_token;
    private  String refreshToken ;
    private  String encodedLogo ;
    private Date tokenExpirationDate ;

}
