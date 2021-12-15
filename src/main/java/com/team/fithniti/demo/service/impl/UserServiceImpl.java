package com.team.fithniti.demo.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.fithniti.demo.config.twilio.SmsRequest;
import com.team.fithniti.demo.dto.request.*;
import com.team.fithniti.demo.dto.response.*;
import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.exception.ResourceExists;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.*;
import com.team.fithniti.demo.repository.*;
import com.team.fithniti.demo.service.FlickrService;
import com.team.fithniti.demo.service.TwilioService;
import com.team.fithniti.demo.service.UserService;
import com.team.fithniti.demo.util.Constants;
import com.team.fithniti.demo.util.RequestState;
import com.team.fithniti.demo.util.UserState;
import com.team.fithniti.demo.util.UserType;
import  com.team.fithniti.demo.validator.UserValidation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
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
    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final RoleRepo roleRepo;
    @Autowired
    private final DriverRepo driverRepo ;
    @Autowired
    private final PassengerRepo passengerRepo ;
    @Autowired
    private final UserRegistrationRequestRepo userRegistrationRequestRepo ;
    @Autowired
    private final TwilioService twilioService ;
    @Autowired
    private final MyUserDetailsService myUserDetailsService;
    @Autowired
    private final UserRecoveryRequestRepo userRecoveryRequestRepo ;
    @Autowired
    private final FlickrService flickrService ;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private InboxRepo inboxRepo;

    @Override
    public List<AppUser> getAll() {
        return userRepo.findAll();
    }

    @Override
    public ResponseEntity<AuthenticationResponse> login(AuthenticationRequest request ) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getPhoneNumber(),request.getPassword()));
        } catch (BadCredentialsException e){
            return new ResponseEntity<>(new InvalidAuthentication("INVALID_CREDENTIALS","Incorrect Credentials ! "),
                    HttpStatus.BAD_REQUEST) ;
        }
        final UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getPhoneNumber());
        AppUser appuser = userRepo.findByPhoneNumber(userDetails.getUsername()).get() ;
        // if the user account is not confirmed
        if (!appuser.isConfirmed())
            return  new ResponseEntity<>(new UnconfirmedAuthentication("NOT_CONFIRMED",appuser.getId(),"Please Confirm Your Account ! "),
                    HttpStatus.BAD_REQUEST)  ;
        if (appuser.getState() == UserState.PERM_BANNED) {
            return new ResponseEntity<>(new InvalidAuthentication("PERMA_BAN","Your account has been banned permanently for violating our code of conduct !"),
                    HttpStatus.BAD_REQUEST) ;
        }
        UUID userId = appuser.getId();
        String userLogo = appuser.getPhotoUrl();
        String role = appuser.getRole().getName() ;
        Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET.getBytes(StandardCharsets.UTF_8)) ;
        Date expiryDate = new Date(System.currentTimeMillis()+ 10000*60*1000) ; // set this back to 10 mins lul , users will die before their token expires :v
        String access_token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(expiryDate)
                .withClaim("role",userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(algorithm) ;

        String refresh_token = JWT.create()
                .withSubject(userDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+ 30*60*1000))
                .sign(algorithm) ;
        return new ResponseEntity<>(new ValidAuthentication("LOGGED_IN",userId,access_token,refresh_token,userLogo,expiryDate,appuser.getState(),appuser.getLastConnectedAs(),role),
                HttpStatus.OK);
    }

    @Override
    public UUID getIdByUsername(String phoneNumber) {
        Optional<AppUser> user = userRepo.findByPhoneNumber(phoneNumber) ;
        if (user.isEmpty()) throw new ResourceNotFound("NOT_FOUND","Wrong phoneNumber !");
        return user.get().getId() ;
    }

    @Override
    public UpdateResponse updateProfile(UUID userId, String newPassword, String newLogoUrl) {
        Optional<AppUser> appUser = userRepo.findById(userId) ;
        if (appUser.isEmpty()) throw new ResourceNotFound("NOT_FOUND","user was not found ! ") ;
        if (newPassword != null ) userRepo.updatePassword(newPassword,userId) ;
        if (newLogoUrl != null) userRepo.updateLogo(newLogoUrl,userId) ;
        return UpdateResponse.builder()
                .status("UPDATED")
                .message("Your Profile has been updated successfully !")
                .build();
    }

    @Override
    public void switchConnectedAs(UUID userId) {
        Optional<AppUser> appUser = userRepo.findById(userId) ;
        if (appUser.isEmpty())
            throw new ResourceNotFound("NOT_FOUND","user was not found ! ") ;
        switch (appUser.get().getLastConnectedAs()){
            case Driver:
                appUser.get().setLastConnectedAs(UserType.Passenger);
                break ;
            case Passenger:
                appUser.get().setLastConnectedAs(UserType.Driver);
                break ;
        }
    }

    @Override
    public void getUserDTO(UUID userId) {

    }


    @Override
    public RegistrationSuccessful create(NewUser user) {
        System.out.println("Hello from servcie ...");
        List<String> errors = UserValidation.validate(user);
        if (!errors.isEmpty())
            throw new InvalidResource(errors, "BAD_REQUEST","Invalid user !");
        boolean exists = userRepo.existsAppUserByPhoneNumber(user.getPhoneNumber()) ;
        if (exists)
            throw new ResourceExists("FOUND","Phone Number Exists Try another !") ;
        AppUser appUser = user.convertToAppUser() ;
        appUser.setState(UserState.ACTIVE);
        appUser.setConfirmed(true);

        appUser.setAlertsCount(0);
        appUser.setLastConnectedAs(UserType.Passenger);
        if(appUser.getPhotoUrl() == null || appUser.getPhotoUrl().equals("")){
            // set default logo
            appUser.setPhotoUrl(flickrService.getDefaultLogo());
        }
        // assign the default user role to all new users
        Optional<Role> userRole = roleRepo.getRoleByName("USER") ;
        userRole.ifPresent(appUser::setRole);
        userRepo.save(appUser);

        //TODO:bullshit maybe delete
        //AppUser appUser1 = (userRepo.findByPhoneNumber(appUser.getPhoneNumber()).get());
        //appUser1.setId(UUID.randomUUID());
        //userRepo.save(appUser1);

        // we assign a driver and a passenger objects relative to our AppUser object
        driverRepo.save(Driver.builder().user(appUser).rating(0f).ridesNumber(0).build()) ;
        passengerRepo.save(Passenger.builder().user(appUser).rating(0f).ridesNumber(0).build()) ;

        //adding inbox
//        inboxRepo.save(Inbox.builder()
//                .userId(appUser.getId())
//                .numberOfMessages(0)
//                .messages(new ArrayList<>())
//                .build());

        // we assign the registration code to the user
        UserRegistrationRequest request = UserRegistrationRequest.builder()
                .user(appUser)
                .requestState(RequestState.PENDING)
                .attemptsNumber(0)
                .verificationCode(String.valueOf((new Random().nextInt(9999999 - 1111111 + 1) + 1111111)))
                .build() ;
        userRegistrationRequestRepo.save(request) ;
         //send sms with twilio
//        twilioService.sendSms(SmsRequest.builder()
//                        .phoneNumber(appUser.getPhoneNumber())
//                        .message("Your <fiThniti> Account Verification Code is "
//                                +request.getVerificationCode())
//                .build());
        return new RegistrationSuccessful(appUser);
    }

    @Override
    public ResponseEntity<VerificationResponse> verifyAccount(UUID user_id , String verificationCode) {
        Optional<UserRegistrationRequest> registrationRequest = userRegistrationRequestRepo.findByUserId(user_id) ;
        if (registrationRequest.isEmpty()) throw new ResourceNotFound("NOT_FOUND","Request was not found !") ;
        UserRegistrationRequest request = registrationRequest.get() ;
        // user has entered the correct verification code
        if (Objects.equals(request.getVerificationCode(), verificationCode)) {
            request.getUser().setConfirmed(true);
            userRegistrationRequestRepo.delete(request);
            //request.setRequestState(RequestState.DONE); // we could set the request state to done or delete it
            return new ResponseEntity<>(VerificationResponse.builder()
                    .verificationState("VERIFIED")
                    .message("Account Has Been Verified Successfully , please log in !")
                    .build(),HttpStatus.OK);
        }else{
            // incorrect code and has attempts left
            if (request.getAttemptsNumber()<2) {
                request.setAttemptsNumber(request.getAttemptsNumber()+1);
                return new ResponseEntity<>(VerificationResponse.builder()
                        .verificationState("FAILED")
                        .message(
                                String.format("Verification code is incorrect , you have %s attempts left " +
                                        "before another code is issued !",(3-request.getAttemptsNumber())))
                        .build(),HttpStatus.BAD_REQUEST);
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
                return new ResponseEntity<>(VerificationResponse.builder()
                        .verificationState("FAILED")
                        .message("Verification code is incorrect , a new code has been issued , please check your phone !")
                        .build(),HttpStatus.BAD_REQUEST);
            }

        }
    }

    @Override
    public RecoveryResponse requestPasswordRecovery(RecoveryRequest recoveryRequest) {
        if (!UserValidation.validatePhoneNumber(recoveryRequest.getPhoneNumber()))
            throw new InvalidResource(List.of("Invalid Phone Number "),
                    "400",
                    "entered phone " +
                            "number does not correspond to a correct phone number format !") ;
        Optional<AppUser> appUser = userRepo.findByPhoneNumber(recoveryRequest.getPhoneNumber()) ;
        if (appUser.isEmpty()) throw new ResourceNotFound("NOT-FOUND","User with associated phone number " +
                "could not be found ,please try another number ! ") ;
        // if an ancient recovery requests exists => delete that mf
        Optional<UserRecoveryRequest> exists = userRecoveryRequestRepo.findByUser(appUser.get()) ;
        exists.ifPresent(userRecoveryRequestRepo::delete);
        // we send the password to the user with the verified number
        String recoveryCode = String.valueOf(new Random().nextInt(9999999 - 1111111 + 1) + 1111111) ;
        UserRecoveryRequest request  = UserRecoveryRequest
                .builder()
                .user(appUser.get())
                .recoveryCode(recoveryCode)
                .build();
        userRecoveryRequestRepo.save(request) ;
        twilioService.sendSms(SmsRequest.builder()
                .phoneNumber(recoveryRequest.getPhoneNumber())
                .message("Your <fiThniti> Recovery Code is "+ recoveryCode+
                        ", enter it to confirm your account ! ")
                .build());
        return RecoveryResponse.builder()
                .status("RECOVERY_ISSUED")
                .message("Recovery Code  has been sent to account with number "+recoveryRequest.getPhoneNumber())
                .build() ;
    }

    @Override
    public ResponseEntity<RecoveryResponse> validateRecoveryCode(RecoveryValidationRequest validationRequest) {
        Optional<UserRecoveryRequest> request = userRecoveryRequestRepo.findByRecoveryCode(validationRequest.getRecoveryCode()) ;
        if (request.isEmpty()) throw new ResourceNotFound("NOT_FOUND","No Recovery Request has " +
                "been found associated with the given code !") ;
        if (Objects.equals(request.get().getUser().getPhoneNumber(), validationRequest.getPhoneNumber()))
            return new ResponseEntity<>(RecoveryResponse.builder()
                    .status("VALIDATED")
                    .message("Recovery code is valid for phone number "+validationRequest.getPhoneNumber())
                    .build(),HttpStatus.OK);
        else {
            return new ResponseEntity<>(RecoveryResponse.builder()
                    .status("NOT_VALIDATED")
                    .message("Invalid Recovery Code !")
                    .build(),HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public RecoveryResponse updateForgottenPassword(UpdatePasswordRequest updateRequest) {
        Optional<UserRecoveryRequest> request = userRecoveryRequestRepo.findByRecoveryCode(updateRequest.getRecoveryCode()) ;
        if (request.isEmpty()) throw new ResourceNotFound("NOT_FOUND","No Recovery Request has " +
                "been found associated with the given code !") ;
        Optional<AppUser> appUser = userRepo.findByPhoneNumber(updateRequest.getPhoneNumber()) ;
        if (appUser.isEmpty()) throw new ResourceNotFound("NOT_FOUND","No user was found who " +
                "has phone number = "+updateRequest.getPhoneNumber()) ;
        if (!UserValidation.validatePassword(updateRequest.getNewPassword()))
            throw new InvalidResource(List.of("Weak Password"),
                    "400",
                    "entered password is weak af , please change it !") ;
        appUser.get().setPassword(new BCryptPasswordEncoder().encode(updateRequest.getNewPassword()));
        userRecoveryRequestRepo.delete(request.get());
        return RecoveryResponse.builder()
                .status("UPDATED_PASSWORD")
                .message("Password has been updated , you may log in again with the new password !")
                .build();
    }

    @Override
    public WarningDismiss dismissWarning(UUID user_id) {
        Optional<AppUser> appUser = userRepo.findById(user_id) ;
        if (appUser.isEmpty()) throw new ResourceNotFound("NOT_FOUND","User with associated phone number " +
                "could not be found ,please try another number ! ") ;
        appUser.get().setState(UserState.ACTIVE);
        return WarningDismiss.builder()
                .status("WARNING_DISMISSED")
                .message("warning has been dismissed !")
                .build();
    }

    @Override
    public RecoveryResponse resendRecoveryPassword(RecoveryRequest request){
        if (!UserValidation.validatePhoneNumber(request.getPhoneNumber()))
            throw new InvalidResource(List.of("Invalid Phone Number "),
                    "400",
                    "entered phone " +
                            "number does not correspond to a correct phone number format !") ;
        Optional<AppUser> appUser = userRepo.findByPhoneNumber(request.getPhoneNumber()) ;
        if (appUser.isEmpty()) throw new ResourceNotFound("NOT_FOUND","could not find an account with given phone number !") ;
        Optional<UserRecoveryRequest> recoveryRequest = userRecoveryRequestRepo.findByUser(appUser.get()) ;
        if (recoveryRequest.isEmpty()) throw new ResourceNotFound("NOT_FOUND","No recovery " +
                "request has been issued for the account associated with "+request.getPhoneNumber()) ;
        twilioService.sendSms(SmsRequest.builder()
                        .phoneNumber(request.getPhoneNumber())
                        .message("Your <fiThniti> Recovery Code is "+recoveryRequest.get().getRecoveryCode())
                .build());
        return RecoveryResponse.builder()
                .status("RECOVERY_RESEND")
                .message("Recovery Code  has been resent to account with number "+request.getPhoneNumber())
                .build();
    }

    //todo - this one should be in the admin controller
    @Override
    public RoleChange changeRole(UUID user_id , Integer role_id) {
        Optional<AppUser> appUser = userRepo.findById(user_id) ;
        if (appUser.isEmpty()) throw new ResourceNotFound("NOT_FOUND","user was not found !") ;
        Optional<Role> role = roleRepo.findById(role_id) ;
        if (role.isEmpty()) throw new ResourceNotFound("NOT_FOUND","role was not found !") ;
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
                Algorithm algorithm = Algorithm.HMAC256(Constants.SECRET.getBytes(StandardCharsets.UTF_8));
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
