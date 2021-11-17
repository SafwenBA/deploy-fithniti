package com.team.fithniti.demo.service;

import com.team.fithniti.demo.dto.response.RideRequestDTO;
import com.team.fithniti.demo.dto.RideRequestFilterOption;
import com.team.fithniti.demo.util.RideRequestState;
import org.springframework.data.domain.Page;

public interface RideRequestService {
    Object create(Long passengerId, Long rideId );
    void cancel(Long passengerId, Long rideId );
    void handle(Long reqId , RideRequestState state);// ans = accept/reject
    Page<RideRequestDTO> getRideRequests(Long rideId, RideRequestFilterOption options);// + filter: state = done, any
    Page<RideRequestDTO> getPassengerRequests(Long passengerId,  RideRequestFilterOption options);//History for passenger requests
}