package com.team.fithniti.demo.config.twilio;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@Data
public class TwilioConfig {
    private final String SID = "AC4dd7968e5d405732a849163774a33af9";
    private final String authToken ="ac60eba8c997e69c57ab64a5e5692daa" ;
    private final String twilioPhoneNumber = "+14422591293";
}
