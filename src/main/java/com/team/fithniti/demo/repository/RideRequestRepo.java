package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Passenger;
import com.team.fithniti.demo.model.Ride;
import com.team.fithniti.demo.model.RideRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface RideRequestRepo extends JpaRepository<RideRequest,Long>, QuerydslPredicateExecutor<RideRequest> {
    RideRequest findByPassengerAndRide(Passenger passenger, Ride ride);
    boolean existsByPassengerAndRide(Passenger passenger, Ride ride);
}
