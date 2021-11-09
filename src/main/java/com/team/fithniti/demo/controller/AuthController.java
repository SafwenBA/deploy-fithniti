package com.team.fithniti.demo.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.team.fithniti.demo.controller.api.AuthAPI;
import com.team.fithniti.demo.dto.*;
import com.team.fithniti.demo.dto.response.AuthenticationResponse;
import com.team.fithniti.demo.service.UserService;
import com.team.fithniti.demo.service.impl.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthController implements AuthAPI {

    @Autowired
    private UserService userService ;

    @Autowired
    private AuthenticationManager authenticationManager;

    private final MyUserDetailsService myUserDetailsService;

    private final String secret = "Wx[3U$NN?Zdc}t*z" ;

    public AuthController(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

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

    @Override
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String phoneNumber, @RequestParam String password) {
        //System.out.println("+" + phoneNumber);
        //System.out.println(password);
        String pn = "+" +phoneNumber;
      try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(pn, password));
      }catch (BadCredentialsException e){
          //throw new Exception("Incorrect phoneNumber or password", e);
          return new ResponseEntity<>(new UserExistsResponseEntity("Invalid user!"), HttpStatus.BAD_REQUEST);
      }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(pn);
        Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8)) ;
        String token = JWT.create()
                .withSubject(userDetails.toString())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 10000*60*1000)).sign(algorithm);

        return ResponseEntity.ok(new AuthenticationResponse(token, userDetails.getUsername()));


    }
}
