package com.team.fithniti.demo.dto;

import com.team.fithniti.demo.util.Order;
import com.team.fithniti.demo.util.RideRequestState;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RideRequestFilterOption {
    private Integer page;// page number
    private Integer limit;// page size limit
    private Order order;//sort order
    private String[] properties;// sort by

    private Float minPassengerRating;
    private RideRequestState requestState;

}
