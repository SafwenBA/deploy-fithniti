package com.team.fithniti.demo.dto;

import com.team.fithniti.demo.model.RidePathway;
import com.team.fithniti.demo.util.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RideFilterOption {
    private LocalTime startTime;
    private LocalDateTime startDateTime;
    private Integer page;// page number
    private Integer limit;// page size limit
    private Order order;//sort order

    private String[] properties;// order by
    private RidePathway pathway;

    private Float minRating;
    private Float maxRating;

    private Float minPrice;
    private Float maxPrice;

}
