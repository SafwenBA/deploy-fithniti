package com.team.fithniti.demo.dto.response;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.util.UserState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private UserState state;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private String photoURL;

    public static UserDTO fromEntity(AppUser user,DriverDto driver, PassengerDto passenger){
        if(user == null)
            throw new InvalidResource(null,"INVALID_ENTITY","Can't map null entity");
        return UserDTO.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber()) //add field to show/hide private data like address or phone
                .state(user.getState())
                .photoURL(user.getPhotoURL())
                .build();
    }
}
