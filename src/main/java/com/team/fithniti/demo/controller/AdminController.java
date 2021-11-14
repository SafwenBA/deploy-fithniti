package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.controller.api.AdminAPI;
import com.team.fithniti.demo.dto.request.AuthenticationRequest;
import com.team.fithniti.demo.dto.request.NewAdmin;
import com.team.fithniti.demo.dto.response.AdminAction;
import com.team.fithniti.demo.dto.response.AdminAuthResponse;
import com.team.fithniti.demo.dto.response.AdminSuccessfulAuth;
import com.team.fithniti.demo.dto.response.RegistrationSuccessful;
import com.team.fithniti.demo.repository.UserRepo;
import com.team.fithniti.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/admin")
public class AdminController implements AdminAPI {

    @Autowired
    private  AdminService adminService ;
    @Autowired
    private  UserRepo userRepo ;

    @Override
    @PostMapping("/ban")
    public AdminAction ban(@RequestParam UUID user_id) {

        return adminService.ban(userRepo.findById(user_id).get()) ;
    }

    @Override
    @PostMapping("/warn")
    public AdminAction warn(@RequestParam UUID user_id) {
        return adminService.warn(userRepo.findById(user_id).get())  ;
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<AdminAuthResponse> login(@RequestBody AuthenticationRequest request) {
     return adminService.login(request) ;
    }

    @Override
    @PostMapping("/register")
    public RegistrationSuccessful registerAdmin(@RequestBody NewAdmin admin) {
        return adminService.registerAdmin(admin) ;

    }
}
