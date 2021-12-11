package com.team.fithniti.demo.dto.request;

import com.team.fithniti.demo.dto.response.CarDTO;
import com.team.fithniti.demo.dto.response.TagDto;
import com.team.fithniti.demo.model.Car;
import com.team.fithniti.demo.model.RidePathway;
import com.team.fithniti.demo.model.Tag;
import com.team.fithniti.demo.util.RideState;
import com.team.fithniti.demo.util.RideType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

/**
 * required fields for creating new ride
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewRide {
    private String description;
    private RidePathway pathway;
    private RideType rideType;
    private Byte maxPlaces;
    private Float price;
    private LocalTime startTime;
//    List<Tag> tags;// List<String>
    private Long idDriver;
    private CarDTO car;

}
