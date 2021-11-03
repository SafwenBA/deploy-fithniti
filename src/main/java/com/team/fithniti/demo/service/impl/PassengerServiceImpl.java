package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.*;
import com.team.fithniti.demo.repository.PassengerRepo;
import com.team.fithniti.demo.repository.RideRepo;
import com.team.fithniti.demo.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class PassengerServiceImpl implements PassengerService {
    @Autowired
    private PassengerRepo passengerRepo;

    @Autowired
    private RideRepo rideRepo;

    @Override
    public Page<Ride> getSubscribedPools(UUID passengerId, Pageable pageable) {
        return (Page<Ride>) passengerRepo.findAllById(passengerId, pageable).stream().map(
                RideRequest::getRide
        ).collect(Collectors.toList());
    }

    @Override
    public PassengerReview createReview(PassengerReview passengerReview, Long rideId) {
        //Assigning the review to the concurrent driver
        rideRepo.findById(rideId).ifPresentOrElse(ride -> {
            Driver driver = ride.getDriver();
            List<PassengerReview> updatedPassengerReviews = Stream.concat(
                    driver.getPassengerReviews().stream(), Stream.of(passengerReview)
                    ).collect(Collectors.toList());
            driver.setPassengerReviews(updatedPassengerReviews);
            ride.setDriver(driver);
            rideRepo.save(ride);
            // TODO: Change exception after implementation
        }, () -> { throw new IllegalStateException("Ride not found"); });

        return passengerReview;
    }
}
