package com.team.fithniti.demo.controller.api;

import com.team.fithniti.demo.dto.request.NewUser;
import com.team.fithniti.demo.dto.response.RecoveryResponse;
import com.team.fithniti.demo.dto.response.RegistrationSuccessful;
import com.team.fithniti.demo.dto.response.VerificationResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public interface AuthAPI {

    RegistrationSuccessful create(NewUser user) ;
    RecoveryResponse passwordRecovery(String phoneNumber) ;
    VerificationResponse verifyAccount(UUID user_id, String verificationCode) ;
    void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException;


}
