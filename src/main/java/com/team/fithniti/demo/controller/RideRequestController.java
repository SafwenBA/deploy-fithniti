package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.controller.api.RideRequestAPI;
import com.team.fithniti.demo.dto.RideRequestFilterOption;
import com.team.fithniti.demo.dto.response.RideRequestDTO;
import com.team.fithniti.demo.service.RideRequestService;
import com.team.fithniti.demo.util.RideRequestState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RideRequestController implements RideRequestAPI {
    @Autowired
    private RideRequestService requestService;

    @Override
    public RideRequestDTO create(Long passengerId, Long rideId) {
        return requestService.create(passengerId,rideId);
    }

    @Override
    public ResponseEntity<String> cancel(Long passengerId, Long rideId) {
        requestService.cancel(passengerId,rideId);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> handle(Long reqId, RideRequestState state) {
        requestService.handle(reqId,state);
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

    @Override
    public Page<RideRequestDTO> getRideRequests(Long rideId, RideRequestFilterOption options) {
        return requestService.getRideRequests(rideId,options);
    }

    @Override
    public Page<RideRequestDTO> getPassengerRequests(Long passengerId, RideRequestFilterOption options) {
        return requestService.getPassengerRequests(passengerId,options);
    }
}
