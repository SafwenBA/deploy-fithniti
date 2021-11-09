package com.team.fithniti.demo.controller;


import com.team.fithniti.demo.controller.api.AuthAPI;
import com.team.fithniti.demo.dto.request.NewUser;
import com.team.fithniti.demo.dto.response.RecoveryResponse;
import com.team.fithniti.demo.dto.response.RegistrationSuccessful;
import com.team.fithniti.demo.dto.response.VerificationResponse;
import com.team.fithniti.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController implements AuthAPI {

    @Autowired
    private final UserService userService ;

    @Override
    @PostMapping("/register")
    public RegistrationSuccessful create(@RequestBody NewUser user) {
        return userService.create(user);
    }

    @Override
    @PostMapping("/recovery/{phoneNumber}")
    public RecoveryResponse passwordRecovery(@PathVariable String phoneNumber) {
        return userService.passwordRecovery(phoneNumber) ;
    }

    @Override
    @PostMapping("/verify")
    public VerificationResponse verifyAccount(@RequestParam UUID user_id,@RequestParam String verificationCode) {
        return userService.verifyAccount(user_id,verificationCode) ;
    }

    @Override
    @PostMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException {
        userService.refreshToken(request,response);
    }
}
