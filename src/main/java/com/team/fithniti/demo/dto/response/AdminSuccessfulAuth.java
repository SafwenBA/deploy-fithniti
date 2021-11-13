package com.team.fithniti.demo.dto.response;

import com.team.fithniti.demo.util.UserState;
import com.team.fithniti.demo.util.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class AdminSuccessfulAuth extends AdminAuthResponse{
    private  String status ;
    private UUID adminId;
    private  String access_token;
    private  String refreshToken ;
    private  String photoUrl ;
    private Date tokenExpirationDate ;
    private String role ;

}
