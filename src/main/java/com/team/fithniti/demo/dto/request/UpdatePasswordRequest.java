package com.team.fithniti.demo.dto.request;

import lombok.Data;

@Data
public class UpdatePasswordRequest {
    private String phoneNumber ;
    private String newPassword;
    private String recoveryCode ;
}
