package com.team.fithniti.demo.service;

import com.team.fithniti.demo.model.PassengerReview;
import com.team.fithniti.demo.model.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DriverService {
    Page<Ride> getMyPools(Long driverId, Pageable pageable);

    PassengerReview createReview(PassengerReview review, Long passengerId);
}
