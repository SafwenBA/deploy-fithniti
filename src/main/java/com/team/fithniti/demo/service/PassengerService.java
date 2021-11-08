package com.team.fithniti.demo.service;

import com.team.fithniti.demo.dto.response.RideDTO;
import com.team.fithniti.demo.model.PassengerReview;
import com.team.fithniti.demo.model.Ride;
import com.team.fithniti.demo.model.RideRequest;
import com.team.fithniti.demo.model.RideReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PassengerService {
    //Get subscribed pools for the passenger
    Page<RideDTO> getSubscribedPools(UUID passengerId, Pageable pageable);

    PassengerReview createReview(PassengerReview review, Long rideId);


}
