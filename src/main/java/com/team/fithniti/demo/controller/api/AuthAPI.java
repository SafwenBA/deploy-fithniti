package com.team.fithniti.demo.controller.api;

import com.team.fithniti.demo.dto.NewUser;
import com.team.fithniti.demo.dto.RecoveryResponse;
import com.team.fithniti.demo.dto.RegistrationSuccessful;
import com.team.fithniti.demo.dto.VerificationResponse;
import com.team.fithniti.demo.model.AppUser;

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
