package com.team.fithniti.demo.service;

import com.team.fithniti.demo.dto.request.*;
import com.team.fithniti.demo.dto.response.*;
import com.team.fithniti.demo.model.AppUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    List<AppUser> getAll();
    AuthenticationResponse login(AuthenticationRequest request) ;
    RegistrationSuccessful create(NewUser user);
    VerificationResponse verifyAccount(UUID user_id , String verificationCode) ;
    RecoveryResponse requestPasswordRecovery(RecoveryRequest recoveryRequest);
    RecoveryResponse resendRecoveryPassword(RecoveryRequest recoveryRequest) ;
    RecoveryResponse validateRecoveryCode(RecoveryValidationRequest validationRequest);
    RecoveryResponse updateForgottenPassword(UpdatePasswordRequest updateRequest);
    WarningDismiss dismissWarning(UUID user_id) ;
    RoleChange changeRole(UUID user_id , Integer role_id) ;
    void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException;
    UUID getIdByUsername(String phoneNumber) ;

}
