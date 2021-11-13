package com.team.fithniti.demo.controller.api;

import com.team.fithniti.demo.dto.request.AuthenticationRequest;
import com.team.fithniti.demo.dto.request.NewAdmin;
import com.team.fithniti.demo.dto.response.AdminAction;
import com.team.fithniti.demo.dto.response.AdminAuthResponse;
import com.team.fithniti.demo.dto.response.AdminSuccessfulAuth;
import com.team.fithniti.demo.dto.response.RegistrationSuccessful;

import java.util.UUID;


public interface AdminAPI {
    AdminAction ban(UUID user_id) ;
    AdminAction warn(UUID user_id) ;
    AdminAuthResponse login(AuthenticationRequest request) ;
    RegistrationSuccessful registerAdmin(NewAdmin admin) ;
}
