package com.team.fithniti.demo.dto.response;

import com.team.fithniti.demo.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class RegistrationSuccessful {
    private UUID id ;
    private String message ;

    public RegistrationSuccessful(AppUser user){
        this.id = user.getId() ;
        this.message = "User is registered successfully , please confirm your account !" ;
    }

    public RegistrationSuccessful(AppUser appUser,String message){
        this.id = appUser.getId() ;
        this.message = message ;
    }
}
