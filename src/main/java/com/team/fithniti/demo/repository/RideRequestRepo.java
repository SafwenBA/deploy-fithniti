package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Passenger;
import com.team.fithniti.demo.model.Ride;
import com.team.fithniti.demo.model.RideRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RideRequestRepo extends JpaRepository<RideRequest,Long> {
    RideRequest findByPassengerAndRide(Passenger passenger, Ride ride);
    boolean existsByPassengerAndRide(Passenger passenger, Ride ride);
    Page<RideRequest> findByRide(Ride ride);
    Page<RideRequest> findByPassenger(Passenger passenger);
}
