package com.team.fithniti.demo.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.team.fithniti.demo.config.twilio.SmsRequest;
import com.team.fithniti.demo.dto.request.AuthenticationRequest;
import com.team.fithniti.demo.dto.request.NewAdmin;
import com.team.fithniti.demo.dto.response.*;
import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceExists;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.model.Role;
import com.team.fithniti.demo.repository.RoleRepo;
import com.team.fithniti.demo.repository.UserRepo;
import com.team.fithniti.demo.service.AdminService;
import com.team.fithniti.demo.service.TwilioService;
import com.team.fithniti.demo.util.Constants;
import com.team.fithniti.demo.util.UserState;
import com.team.fithniti.demo.util.UserType;
import com.team.fithniti.demo.validator.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

    @Autowired
    private final UserRepo userRepo ;
    @Autowired
    private final RoleRepo roleRepo ;
    @Autowired
    private final AuthenticationManager authenticationManager;
    @Autowired
    private final MyUserDetailsService myUserDetailsService;
    @Autowired
    private final TwilioService twilioService ;

    @Override
    public AdminAction ban(AppUser appUser) {
        boolean exists = userRepo.existsById(appUser.getId()) ;
        if (!exists)
            throw new ResourceNotFound("NOT_FOUND","user was not found !") ;
        appUser.setState(UserState.PERM_BANNED);
        twilioService.sendSms(SmsRequest.builder()
                        .phoneNumber(appUser.getPhoneNumber())
                        .message("Your <fithniety> Account has been banned for good ," +
                                " say goodbye to our services mothaf##a!")
                .build());
        return AdminAction.builder()
                .status("BAN_ISSUED")
                .message("Ban has been issued to user with id "+appUser.getId())
                .build();
    }

    //user has 3 warnings before he gets jebaited (banned) :v
    @Override
    public AdminAction warn(AppUser appUser) {
        boolean exists = userRepo.existsById(appUser.getId()) ;
        if (!exists)
            throw new ResourceNotFound("NOT_FOUND","user was not found !") ;
        appUser.setAlertsCount(appUser.getAlertsCount()+1);
        if(appUser.getAlertsCount()<3 && appUser.getState()!=UserState.PERM_BANNED){
            appUser.setState(UserState.WARNED);
            return AdminAction.builder()
                    .status("WARNING_ISSUED")
                    .message("Warning has been issued to user with id "+appUser.getId())
                    .build();
        }else
            return ban(appUser) ;
        }

    @Override
    public ResponseEntity<?> login(AuthenticationRequest request) {
        //AdminAuthResponse
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhoneNumber(), request.getPassword()));
        } catch (BadCredentialsException e){
            return new ResponseEntity(new AdminUnsuccessfulAuth("Invalid user!"), HttpStatus.BAD_REQUEST) ;
        }

        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getPhoneNumber());
        AppUser appuser = userRepo.findByPhoneNumber(userDetails.getUsername()).get() ;
        String role = appuser.getRole().getName() ;
        if (Objects.equals(role, "USER"))
            return new ResponseEntity(new AdminUnsuccessfulAuth("Invalid user!"), HttpStatus.BAD_REQUEST) ;
        String userLogo = appuser.getPhotoUrl() ;
        UUID userId = appuser.getId();
        Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET.getBytes(StandardCharsets.UTF_8)) ;
        // TODO: 11/12/2021 set this back to 10 mins lul , users will die before their token expires :v
        Date expiryDate = new Date(System.currentTimeMillis()+ 10000*60*1000) ; 
        String access_token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(expiryDate)
                .withClaim("role",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm) ;

        String refresh_token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 30*60*1000))
                .sign(algorithm) ;
        return ResponseEntity.ok(AdminSuccessfulAuth.builder()
                .adminId(userId)
                .access_token(access_token)
                .refreshToken(refresh_token)
                .photoUrl(userLogo)
                .tokenExpirationDate(expiryDate)
                .role(role)
                .build());
    }

    @Override
    public RegistrationSuccessful registerAdmin(NewAdmin admin) {
        List<String> errors = UserValidation.validateAdmin(admin);
        if (!errors.isEmpty())
            throw new InvalidResource(errors, "BAD_REQUEST","Invalid user !");
        boolean exists = userRepo.existsAppUserByPhoneNumber(admin.getPhoneNumber()) ;
        if (exists)
            throw new ResourceExists("FOUND","Phone Number Exists Try another !") ;
        AppUser appUser = admin.convertToAppUser() ;
        appUser.setLastConnectedAs(UserType.Passenger);
        if(appUser.getPhotoUrl() == null || appUser.getPhotoUrl().equals("")){
            // set default logo
            //todo - uncomment this when image service is active
            //appUser.setPhotoUrl(imageService.getDefaultLogo());
        }
        Optional<Role> userRole = roleRepo.getRoleByName("ADMIN") ;
        userRole.ifPresent(appUser::setRole);
        userRepo.save(appUser) ;
        return new RegistrationSuccessful(appUser,"Admin Account has been created Successfully ! ") ;
    }
}
