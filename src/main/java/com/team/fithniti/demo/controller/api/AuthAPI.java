package com.team.fithniti.demo.controller.api;

import com.team.fithniti.demo.dto.request.*;
import com.team.fithniti.demo.dto.response.RecoveryResponse;
import com.team.fithniti.demo.dto.response.RegistrationSuccessful;
import com.team.fithniti.demo.dto.response.VerificationResponse;
import com.team.fithniti.demo.dto.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public interface AuthAPI {

    RegistrationSuccessful create(NewUser user) ;
    ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request) throws Exception;
    RecoveryResponse requestPasswordRecovery(RecoveryRequest request);
    ResponseEntity<RecoveryResponse> validateRecoveryCode( RecoveryValidationRequest request);
    ResponseEntity<VerificationResponse> verifyAccount(UUID user_id, String verificationCode) ;
    RecoveryResponse updateForgottenPassword( UpdatePasswordRequest request);
    RecoveryResponse resendRecoveryPassword(RecoveryRequest request) ;

    void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException;

}
