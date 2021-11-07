package com.team.fithniti.demo.dto;

import com.team.fithniti.demo.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class RegistrationSuccessful {
    private UUID id ;
    private String message ;

    public RegistrationSuccessful(AppUser user){
        this.id = user.getId() ;
        this.message = "User is registered successfully , please confirm your account !" ;
    }
}
