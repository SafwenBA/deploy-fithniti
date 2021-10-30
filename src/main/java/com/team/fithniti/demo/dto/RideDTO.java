package com.team.fithniti.demo.dto;

import com.team.fithniti.demo.model.Driver;
import com.team.fithniti.demo.model.RidePathway;
import com.team.fithniti.demo.model.Tag;
import com.team.fithniti.demo.util.RideState;
import com.team.fithniti.demo.util.RideType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideDTO {
    private Long id;
    private String description;
    private RidePathway pathway;
    private RideType rideType;
    private RideState rideState;
    private Byte availablePlaces;
    private Byte maxPlaces;
    private Byte ratersNumber;
    private Float rating;
    private Float price;
    private LocalDateTime startDate;
    private LocalTime startTime;
    private LocalTime arrivalTime;
    private Driver driver; // TODO: 10/30/21 replace Driver with DriverDTO
    List<Tag> tags;// TODO: 10/30/21 same thing, replace with DTO + check if tag is not null before mapping
}
