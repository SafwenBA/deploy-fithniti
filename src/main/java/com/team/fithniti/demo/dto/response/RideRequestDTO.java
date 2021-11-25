package com.team.fithniti.demo.dto.response;

import com.team.fithniti.demo.model.Passenger;
import com.team.fithniti.demo.model.Ride;
import com.team.fithniti.demo.model.RideRequest;
import com.team.fithniti.demo.util.RideRequestState;
import lombok.Builder;
import lombok.Data;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@Builder
public class RideRequestDTO {
    private Long id;
    private RideRequestState state;
    private PassengerDto passenger;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public static RideRequestDTO fromEntity(RideRequest rideRequest){
        return RideRequestDTO.builder()
                .id(rideRequest.getId())
                .state(rideRequest.getState())
                .passenger(PassengerDto.fromEntity(rideRequest.getPassenger()))
                .createdDate(rideRequest.getCreatedDate())
                .updatedDate(rideRequest.getLastModifiedDate())
                .build();
    }
}
