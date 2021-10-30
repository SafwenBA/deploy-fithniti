package com.team.fithniti.demo.service;

import com.team.fithniti.demo.model.Passenger;
import com.team.fithniti.demo.model.Ride;

import java.util.List;

public interface RideService {
    Ride create(Ride ride);
    List<Ride> getAll();// Filter: Query DSL
    List<Ride> getDriverRides(Long driverId);// Filter: Query DSL

    void deleteById(Long rideId);
    void update(Long rideId);// update : state ...
//    void rate(PassengerReview);

//    Request Service
    void joinRide(Long rideID, Long passengerID);
    void acceptRequest(Long rideID);
    void rejectRequest(Long rideID);
    void cancelRequest(Long rideID, Long passengerID);


}
