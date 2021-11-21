package com.team.fithniti.demo.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team.fithniti.demo.exception.InvalidResource;
import com.team.fithniti.demo.model.Driver;
import com.team.fithniti.demo.model.Ride;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideDTO {
    private Long id;
    private String description;
    private RidePathwayDto pathway;
    private RideType rideType;
    private RideState rideState;
    private Byte availablePlaces;
    private Byte maxPlaces;
    private Byte ratersNumber;
    private Float rating;
    private Float price;
    @JsonFormat(pattern = "dd-mm-yyyy")
    private LocalDateTime startDate;
    private LocalTime startTime;
    private LocalTime arrivalTime;
    private DriverDto driver;
    List<TagDto> tags;
    private CarDTO car;
    public static RideDTO fromEntity(Ride ride){
        if ( ride == null )
            throw new InvalidResource(null,"INVALID_ENTITY","Can't map null entity");
        return RideDTO.builder()
                .id(ride.getId())
                .description(ride.getDescription())
                .pathway(RidePathwayDto.fromEntity(ride.getPathway()))
                .rideType(ride.getRideType())
                .driver(DriverDto.fromEntity(ride.getDriver()))
                .rideState(ride.getRideState())
                .availablePlaces(ride.getAvailablePlaces())
                .maxPlaces(ride.getMaxPlaces())
                .rating(ride.getRating())
                .ratersNumber(ride.getRatersNumber())
                .price(ride.getPrice())
                .car(CarDTO.fromEntity(ride.getCar(), ride.getCarModel()))
                .startDate(ride.getCreatedDate())
                .startTime(ride.getStartTime())
                .arrivalTime(ride.getArrivalTime())
                .tags( ( ride.getTags() == null ) ? new ArrayList<>() :
                        ride.getTags().stream().map(TagDto::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
