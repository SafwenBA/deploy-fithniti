package com.team.fithniti.demo.dto.request;

import lombok.Data;

@Data
public class RecoveryValidationRequest {
    private String recoveryCode;
    private String phoneNumber ;
}
