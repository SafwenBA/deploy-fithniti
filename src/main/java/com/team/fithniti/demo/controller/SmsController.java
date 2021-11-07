package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.config.twilio.SmsRequest;
import com.team.fithniti.demo.service.TwilioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// for testing purposes only --> should be deleted
@RestController
@RequestMapping("/sms")
public class SmsController {
    private final TwilioService twilioService ;

    @Autowired
    public SmsController(TwilioService twilioService) {
        this.twilioService = twilioService;
    }

    @PostMapping
    public void sendSms(@RequestBody SmsRequest request){
        twilioService.sendSms(request);
    }
}
