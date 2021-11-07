package com.team.fithniti.demo.config.twilio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class SmsRequest {
    private String phoneNumber ;
    private String message ;
}
