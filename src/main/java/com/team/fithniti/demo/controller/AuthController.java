package com.team.fithniti.demo.controller;


import com.team.fithniti.demo.controller.api.AuthAPI;
import com.team.fithniti.demo.dto.request.*;
import com.team.fithniti.demo.dto.response.AuthenticationResponse;
import com.team.fithniti.demo.dto.response.RecoveryResponse;
import com.team.fithniti.demo.dto.response.RegistrationSuccessful;
import com.team.fithniti.demo.dto.response.VerificationResponse;
import com.team.fithniti.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController implements AuthAPI {

    @Autowired
    private UserService userService ;
    private final String secret = "Wx[3U$NN?Zdc}t*z" ;


    @Override
    @PostMapping("/register")
    public RegistrationSuccessful create(@RequestBody NewUser user) {
        return userService.create(user);
    }



    @Override
    @PostMapping("/recovery")
    public RecoveryResponse requestPasswordRecovery(@RequestBody RecoveryRequest request) {
        return userService.requestPasswordRecovery(request) ;
    }

    @Override
    @PostMapping("/verify")
    public VerificationResponse verifyAccount(@RequestParam UUID user_id, @RequestParam String verificationCode) {
        return userService.verifyAccount(user_id,verificationCode) ;
    }

    @Override
    @PostMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException {
        userService.refreshToken(request,response);
    }


    @Override
    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        return userService.login(request) ;
    }

    @Override
    @PostMapping("/recovery/validate")
    public RecoveryResponse validateRecoveryCode(@RequestBody RecoveryValidationRequest request) {
        return userService.validateRecoveryCode(request);
    }

    @Override
    @PostMapping("/recovery/update")
    public RecoveryResponse updateForgottenPassword(@RequestBody UpdatePasswordRequest request) {
        return userService.updateForgottenPassword(request);
    }
}
