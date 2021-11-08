package com.team.fithniti.demo.service;

import com.team.fithniti.demo.util.RideRequestState;
import org.springframework.data.domain.Page;

public interface RideRequestService {
    // TODO: 11/7/21 replace object with ?
    Object create(Long passengerId, Long rideId );
    void cancel(Long passengerId, Long rideId );
    void handle(Long reqId , RideRequestState state);// ans = accept/reject
    Page<Object> getRideRequests(Long rideId);// + filter: state = done, any
    Page<Object> getPassengerRequests(Long rideId, Long passengerId);//History for passenger requests
}