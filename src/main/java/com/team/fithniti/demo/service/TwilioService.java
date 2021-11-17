package com.team.fithniti.demo.service;

import com.team.fithniti.demo.config.twilio.SmsRequest;

public interface TwilioService {
    void sendSms(SmsRequest smsRequest) ;
}
