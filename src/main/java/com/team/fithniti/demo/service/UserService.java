package com.team.fithniti.demo.service;

import com.team.fithniti.demo.dto.*;
import com.team.fithniti.demo.model.AppUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    List<AppUser> getAll();
    RegistrationSuccessful create(NewUser user);
    RecoveryResponse passwordRecovery(String phoneNumber) ;
    VerificationResponse verifyAccount(UUID user_id , String verificationCode) ;
    RoleChange changeRole(UUID user_id , Integer role_id) ;
    void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException;
    UUID getIdByUsername(String phoneNumber) ;

}
