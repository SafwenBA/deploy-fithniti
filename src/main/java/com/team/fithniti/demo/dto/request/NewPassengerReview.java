package com.team.fithniti.demo.dto.request;

import com.team.fithniti.demo.model.Driver;
import com.team.fithniti.demo.model.Passenger;
import com.team.fithniti.demo.model.ReviewType;
import com.team.fithniti.demo.model.Ride;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewPassengerReview {
    Long id;
    private Byte rating;
    private String comment;
    private Long reviewTypeId;
    private Long driverId;
    private Passenger passenger;
    private Long rideId;
}
