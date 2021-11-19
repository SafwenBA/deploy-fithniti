package com.team.fithniti.demo.controller.api;

import com.team.fithniti.demo.dto.request.*;
import com.team.fithniti.demo.dto.response.RecoveryResponse;
import com.team.fithniti.demo.dto.response.RegistrationSuccessful;
import com.team.fithniti.demo.dto.response.VerificationResponse;
import com.team.fithniti.demo.dto.response.AuthenticationResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public interface AuthAPI {
    @ApiOperation(value = "Create",notes = "this method will add a new user to the system #NoShitSherlok",responseContainer = "RegistrationSuccessful")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "BAD_REQUEST"),
            @ApiResponse(code = 302, message = "FOUND")
    })
    RegistrationSuccessful create(NewUser user) ;
    //---------------------------------------------------------------
    @ApiOperation(value = "login",notes = "this method will log in the user if the credentials are correct #NoShitSherlok",responseContainer = "ResponseEntity<AuthenticationResponse>")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "BAD_REQUEST")
    })
    ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request) throws Exception;
    //---------------------------------------------------------------
    @ApiOperation(value = "RequestPasswordRecovery",notes = "this method will send an sms with the recovery code " +
            "to the user with the given phone number ",responseContainer = "RecoveryResponse")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "BAD_REQUEST")
    })
    RecoveryResponse requestPasswordRecovery(RecoveryRequest request);
    //---------------------------------------------------------------
    @ApiOperation(value = "ValidateRecoveryCode",notes = "this method will take in the recovery code and the phoneNumber" +
            " and check if the entered code is the one that was sent or not",responseContainer = "ResponseEntity<RecoveryResponse>")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "BAD_REQUEST")
    })
    ResponseEntity<RecoveryResponse> validateRecoveryCode( RecoveryValidationRequest request);

    //---------------------------------------------------------------
    @ApiOperation(value = "verifyAccount",notes = "this method will confirm the phone number of the account => Account Confirmed" +
            " with the given user_id "
            ,responseContainer = "ResponseEntity<VerificationResponse>")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "BAD_REQUEST")
    })
    ResponseEntity<VerificationResponse> verifyAccount(UUID user_id, String verificationCode) ;
    //---------------------------------------------------------------
    @ApiOperation(value = "updateForgottenPassword",notes = "this method will let the user update his forgotten password"
            ,responseContainer = "RecoveryResponse")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "BAD_REQUEST")
    })
    RecoveryResponse updateForgottenPassword( UpdatePasswordRequest request);
    //---------------------------------------------------------------
    @ApiOperation(value = "ResendRecoveryPassword",notes = "this method will let the user resend the recovery code" +
            " to his phoneNumber incase he didn't receive it"
            ,responseContainer = "RecoveryResponse")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "BAD_REQUEST")
    })
    RecoveryResponse resendRecoveryPassword(RecoveryRequest request) ;
    //---------------------------------------------------------------
    @ApiOperation(value = "refreshToken",notes = "this method will let the user acquire a new token in case of " +
            "the expiration of the old one"
            ,responseContainer = "RecoveryResponse")
    @ApiResponses(value = {
            @ApiResponse(code = 200 ,message = "OK"),
            @ApiResponse(code = 400 ,message = "BAD_REQUEST")
    })
    void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException;

}
