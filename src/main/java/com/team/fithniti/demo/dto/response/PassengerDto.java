package com.team.fithniti.demo.dto.response;

import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.model.Passenger;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerDto {
    private Long id;
    private double rating;
    private Integer ridesNumber;
    private UserDTO user;

    public static PassengerDto fromEntity(Passenger passenger){
        if (passenger == null)
            throw new InvalidResource(null,"INTERNAL ERROR","Can't map null entity");
        return PassengerDto.builder()
                .id(passenger.getId())
                .rating(passenger.getRating())
                .ridesNumber(passenger.getRidesNumber())
                .user(UserDTO.fromEntity(passenger.getUser()))
                .build();
    }

}
