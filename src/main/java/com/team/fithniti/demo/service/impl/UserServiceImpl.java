package com.team.fithniti.demo.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.fithniti.demo.config.twilio.SmsRequest;
import com.team.fithniti.demo.dto.*;
import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceExists;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.*;
import com.team.fithniti.demo.repository.*;
import com.team.fithniti.demo.service.TwilioService;
import com.team.fithniti.demo.service.UserService;
import com.team.fithniti.demo.util.RequestState;
import com.team.fithniti.demo.util.UserState;
import  com.team.fithniti.demo.validator.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final DriverRepo driverRepo ;
    private final PassengerRepo passengerRepo ;
    private final UserRegistrationRequestRepo userRegistrationRequestRepo ;
    private final TwilioService twilioService ;
    //todo - uncomment this after merge
    //private final ImageService imageService ;
    private final String secret = "Wx[3U$NN?Zdc}t*z" ;


//    @Override
//    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
//        Optional<AppUser> user = userRepo.findByPhoneNumber(phoneNumber) ;
//        if ( user.isEmpty() ) throw new UsernameNotFoundException("phoneNumber was not found ! ");
//        AppUser userData = user.get()  ;
//        return new User(userData.getUsername(),userData.getPassword(),userData.getAuthorities());
//    }

    @Override
    public List<AppUser> getAll() {
        return userRepo.findAll();
    }

    @Override
    public UUID getIdByUsername(String phoneNumber) {
        Optional<AppUser> user = userRepo.findByPhoneNumber(phoneNumber) ;
        if (user.isEmpty()) throw new ResourceNotFound("Wrong phoneNumber !");
        return user.get().getId() ;
    }


    @Override
    public RegistrationSuccessful create(NewUser user) {
        List<String> errors = UserValidation.validate(user);
        if (!errors.isEmpty())
            throw new InvalidResource(errors, "BAD_REQUEST","Invalid user !");
        boolean exists = userRepo.existsAppUserByPhoneNumber(user.getPhoneNumber()) ;
        if (exists)
            throw new ResourceExists("FOUND","Phone Number Exists Try another !") ;
        AppUser appUser = user.convertToAppUser() ;
        appUser.setState(UserState.ACTIVE);
        if (appUser.getEncodedLogo() == null || appUser.getEncodedLogo().equals("")){
            // set default logo
            //appUser.setEncodedLogo(imageService.loadDefault());
        }
        userRepo.save(appUser) ;
        // we assign a driver and a passenger objects relative to our AppUser object
        driverRepo.save(Driver.builder().user(appUser).rating(0f).ridesNumber(0).build()) ;
        passengerRepo.save(Passenger.builder().user(appUser).rating(0f).ridesNumber(0).build()) ;
        // assign the default user role to all new users
        Optional<Role> userRole = roleRepo.getRoleByName("USER") ;
        userRole.ifPresent(appUser::setRole);
        // we assign the registration code to the user
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                .user(appUser)
                .requestState(RequestState.PENDING)
                .attemptsNumber(0)
                .verificationCode(String.valueOf((new Random().nextInt(9999999 - 1111111 + 1) + 1111111)))
                .build() ;
        userRegistrationRequestRepo.save(request) ;
        // send sms with twilio
//        twilioService.sendSms(SmsRequest.builder()
//                        .phoneNumber(appUser.getPhoneNumber())
//                        .message("Your <fiThniti> Account Verification Code is "
//                                +request.getVerificationCode())
//                .build());
        return new RegistrationSuccessful(appUser);
    }

    @Override
    public VerificationResponse verifyAccount(UUID user_id , String verificationCode) {
        Optional<UserRegistrationRequest> registrationRequest = userRegistrationRequestRepo.findByUserId(user_id) ;
        if (registrationRequest.isEmpty()) throw new ResourceNotFound("Request was not found !") ;
        UserRegistrationRequest request = registrationRequest.get() ;
        // user has entered the correct verification code
        if (Objects.equals(request.getVerificationCode(), verificationCode)) {
            request.getUser().setConfirmed(true);
            userRegistrationRequestRepo.delete(request);
            //request.setRequestState(RequestState.DONE); // we could set the request state to done or delete it
            return VerificationResponse.builder()
                            .verificationState("VERIFIED")
                    .message("Account Has Been Verified Successfully , please log in !")
                    .build();
        }else{
            // incorrect code and has attempts left
            if (request.getAttemptsNumber()<2) {
                request.setAttemptsNumber(request.getAttemptsNumber()+1);
                return VerificationResponse.builder()
                        .verificationState("FAILED")
                        .message(
                                String.format("Verification code is incorrect , you have %s attempts left " +
                                        "before another code is issued !",(3-request.getAttemptsNumber())))
                        .build();
            }
            else {
                // incorrect code and has no attempts left -> issue a new code
                //request.setRequestState(RequestState.REJECTED);  we could delete the request or change its state to rejected
                userRegistrationRequestRepo.delete(request);
                UserRegistrationRequest newRequest = UserRegistrationRequest.builder()
                        .user(request.getUser())
                        .requestState(RequestState.PENDING)
                        .attemptsNumber(0)
                        .verificationCode(String.valueOf((new Random().nextInt(9999999 - 1111111 + 1) + 1111111)))
                        .build() ;
                userRegistrationRequestRepo.save(newRequest) ;
                // we send the new verification code
                twilioService.sendSms(SmsRequest.builder()
                        .phoneNumber(request.getUser().getPhoneNumber())
                        .message("Your <fiThniti> Account Verification Code is "
                                +newRequest.getVerificationCode())
                        .build());
                return VerificationResponse.builder()
                        .verificationState("FAILED")
                        .message("Verification code is incorrect , a new code has been issued , please check your phone !")
                        .build();
            }

        }
    }

    //todo - this one is dump ngl , a rework is needed probably
    @Override
    public RecoveryResponse passwordRecovery(String phoneNumber) {
        if (!UserValidation.validatePhoneNumber(phoneNumber))
            throw new InvalidResource(List.of("Invalid Phone Number "),
                    "400",
                    "entered phone " +
                            "number does not correspond to a correct phone number format !") ;
        Optional<AppUser> appUser = userRepo.findByPhoneNumber(phoneNumber) ;
        if (appUser.isEmpty()) throw new ResourceNotFound("User with associated phone number " +
                "could not be found ,please try another number ! ") ;
        // we send the password to the user with the verified number
        int temporaryPassword = new Random().nextInt(9999999 - 1111111 + 1) + 1111111 ;
        appUser.get().setPassword(new BCryptPasswordEncoder().encode(Integer.toString(temporaryPassword)));
        twilioService.sendSms(SmsRequest.builder()
                .phoneNumber(phoneNumber)
                .message("Your <fiThniti> Temporary Account Password is "+ temporaryPassword+
                        ", you should change it when you log in !")
                .build());
        return RecoveryResponse.builder()
                .status("RECOVERY-ISSUED")
                .message("Temporary Password has been issued to account with number "+phoneNumber)
                .build() ;
    }

    //todo - this one should be in the admin controller
    @Override
    public RoleChange changeRole(UUID user_id , Integer role_id) {
        Optional<AppUser> appUser = userRepo.findById(user_id) ;
        if (appUser.isEmpty()) throw new ResourceNotFound("user was not found !") ;
        Optional<Role> role = roleRepo.findById(role_id) ;
        if (role.isEmpty()) throw new ResourceNotFound("role was not found !") ;
        appUser.get().setRole(role.get());
        return RoleChange.builder()
                .status("ROLE_CHANGED")
                .message(String.format("User %s %s role has been changed to %s",
                        appUser.get().getFirstName(),
                        appUser.get().getLastName(),
                        role.get().getName()))
                .build();
    }

    @Override
    public void refreshToken(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256(secret.getBytes(StandardCharsets.UTF_8));
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AppUser user = userRepo.findByPhoneNumber(username).get() ;
                String access_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ 10*60*1000)) // 10 minutes
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", Stream.of(user.getRole()).map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm) ;


                String new_refresh_token = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis()+ 30*60*1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", Stream.of(user.getRole()).map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm) ;
                Map<String,String> tokensDic = new HashMap<>() ;
                tokensDic.put("access_token",access_token) ;
                tokensDic.put("refresh_token",new_refresh_token) ;
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokensDic);

            } catch (Exception e) {
                // catch bloc
                response.setStatus(FORBIDDEN.value());
                Map<String,String> error = new HashMap<>() ;
                error.put("Error",e.getMessage()) ;
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);

            }
        }else{
            throw new RuntimeException("the refresh token is missing") ;
        }

    }



}
