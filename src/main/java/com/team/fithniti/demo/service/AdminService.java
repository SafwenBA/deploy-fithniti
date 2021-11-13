package com.team.fithniti.demo.service;

import com.team.fithniti.demo.dto.request.AuthenticationRequest;
import com.team.fithniti.demo.dto.request.NewAdmin;
import com.team.fithniti.demo.dto.response.AdminAction;
import com.team.fithniti.demo.dto.response.AdminAuthResponse;
import com.team.fithniti.demo.dto.response.AdminSuccessfulAuth;
import com.team.fithniti.demo.dto.response.RegistrationSuccessful;
import com.team.fithniti.demo.model.AppUser;

public interface AdminService {
    AdminAction ban(AppUser appUser) ;
    AdminAction warn(AppUser appUser) ;
    AdminAuthResponse login(AuthenticationRequest request) ;
    RegistrationSuccessful registerAdmin(NewAdmin admin) ;
}
