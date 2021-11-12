package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.dto.response.RideDTO;
import com.team.fithniti.demo.exception.ResourceNotFound;
import com.team.fithniti.demo.model.Passenger;
import com.team.fithniti.demo.model.Ride;
import com.team.fithniti.demo.model.RideRequest;
import com.team.fithniti.demo.repository.RideRequestRepo;
import com.team.fithniti.demo.service.RideRequestService;
import com.team.fithniti.demo.service.RideService;
import com.team.fithniti.demo.util.RideRequestState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class RideRequestServiceImpl implements RideRequestService {
    @Autowired
    private RideRequestRepo rideRequestRepo;
    @Autowired
    private RideService rideService;

    @Override
    public Object create(Long passengerId, Long rideId) {
        Passenger passenger = new Passenger();//Todo: replace with passengerService.findById(id)
        Ride ride = new Ride() ; // Todo: rideService.findById(rideId)
        if ( rideRequestRepo.existsByPassengerAndRide(passenger,ride)){
            System.out.println("Request already submitted");
            // throw exception
        }
        RideRequest rideRequest = RideRequest.builder()
                .ride(ride)
                .passenger(passenger)
                .state(RideRequestState.PENDING)
                .build();
        return rideRequestRepo.save(rideRequest);
        // TODO: 11/7/21 notification to driver
    }

    @Override
    public void cancel(Long passengerId, Long rideId) {
        Passenger passenger = new Passenger();//Todo: replace with passengerService.findById(id)
        Ride ride = new Ride() ; // Todo: rideService.findById(rideId)
//      check if passenger.getUser() == authUser ; todo
//        check if status != in progress, done

        if ( ! rideRequestRepo.existsByPassengerAndRide(passenger,ride)){
            System.out.println("No Request found");
            // throw exception
        }
        rideRequestRepo.delete(rideRequestRepo.findByPassengerAndRide(passenger,ride));
    }

    @Override
    public void handle(Long reqId, RideRequestState state) {
        RideRequest rideRequest = rideRequestRepo.findById(reqId).orElseThrow(
                () -> new ResourceNotFound("RIDE_REQUEST_FAILED", "No request found"));
        rideRequest.setState(state);
        // TODO: 11/7/21 Generate notification to passenger
        rideRequestRepo.save(rideRequest);
    }

    @Override
    public Page<Object> getRideRequests(Long rideId) {
        Ride ride = new Ride() ; // todo
        //? ride.getDriver().getUser().equals(auth user) todo fun checkUserAccess(...)
        rideRequestRepo.findByRide(ride); //todo
        // TODO: rideReqDTO --> { passenger id, user info, rating as passenger 4.33 **** / total rides 1200 , date submit }
        return null;
    }

    @Override
    public Page<Object> getPassengerRequests(Long rideId,Long passengerId) {
        Passenger passenger = new Passenger(); // todo
        rideRequestRepo.findByPassenger(passenger);

        return null;
    }
}