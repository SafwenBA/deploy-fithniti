package com.team.fithniti.demo.config.twilio;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
@Data
public class TwilioConfig {
    private final String SID = "AC7b5c717f0cd01f577a46ae6b60a62f79";
    private final String authToken ="e3dc4302c2a2032fd7ccbaf1ff29c0b" ;
    private final String twilioPhoneNumber = "+17196243919";
}
