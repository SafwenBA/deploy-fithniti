package com.team.fithniti.demo.dto.request;

import com.team.fithniti.demo.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Data
@AllArgsConstructor
@Builder
public class NewAdmin {
    private String phoneNumber;
    private String password ;
    private String firstName ;
    private String lastName ;

    public AppUser convertToAppUser(){
        return AppUser.builder()
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .phoneNumber(this.getPhoneNumber())
                .password(new BCryptPasswordEncoder().encode(this.getPassword()))
                .build() ;
    }
}
