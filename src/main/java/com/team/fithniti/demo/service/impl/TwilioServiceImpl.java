package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.config.twilio.SmsRequest;
import com.team.fithniti.demo.config.twilio.TwilioConfig;
import com.team.fithniti.demo.service.TwilioService;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TwilioServiceImpl implements TwilioService {
    private final TwilioConfig twilioConfig ;

    @Autowired
    public TwilioServiceImpl(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        MessageCreator creator = Message.creator(
                new PhoneNumber(smsRequest.getPhoneNumber()),
                new PhoneNumber(twilioConfig.getTwilioPhoneNumber()),
                smsRequest.getMessage()
        );
        creator.create() ;
    }
}
