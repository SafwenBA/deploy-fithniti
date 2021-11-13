package com.team.fithniti.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.util.UserState;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.Date;

/**
 * Only required fields for registration
 */
@AllArgsConstructor
@Data
public class NewUser {
    private String phoneNumber;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private LocalDate birthDate;
    private String photoUrl ;


    public AppUser convertToAppUser(){
        System.out.println("LOADED PHOTO URL = "+getPhotoUrl()+" ..... !");
        return AppUser.builder()
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .phoneNumber(this.getPhoneNumber())
                .password(new BCryptPasswordEncoder().encode(this.getPassword()))
                .photoUrl(this.getPhotoUrl())
                .address(this.getAddress())
                .birthDate(this.getBirthDate())
                .build() ;
    }

}
