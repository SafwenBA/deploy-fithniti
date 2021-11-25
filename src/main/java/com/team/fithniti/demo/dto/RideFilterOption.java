package com.team.fithniti.demo.dto;

import com.team.fithniti.demo.dto.response.CarDTO;
import com.team.fithniti.demo.model.RidePathway;
import com.team.fithniti.demo.model.Tag;
import com.team.fithniti.demo.util.Order;
import com.team.fithniti.demo.util.RideState;
import com.team.fithniti.demo.util.RideType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideFilterOption {
    private LocalTime startTimeFrom;
    private LocalTime startTimeTo;
    private LocalDate startDate;

    private Integer page;// page number
    private Integer limit;// page size limit
    private Order order;//sort order

    private String[] properties;// order by
    private RidePathway pathway;

    private Float minDriverRating;
    private Float maxDriverRating;

    private Float minPrice;
    private Float maxPrice;

    private Byte availablePlaces;
    private Byte maxPlaces;

    private RideType rideType;
    private RideState rideState;

    private List<String> tags;

    private String carBrand;
    private String carModel;


}
