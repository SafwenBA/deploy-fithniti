package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.controller.api.RideAPI;
import com.team.fithniti.demo.dto.RideFilterOption;
import com.team.fithniti.demo.dto.request.NewRide;
import com.team.fithniti.demo.dto.response.RideDTO;
import com.team.fithniti.demo.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/rides")
@CrossOrigin(origins = "*")
public class RideController implements RideAPI {
    @Autowired
    private RideService rideService;

    @Override
    public RideDTO create(NewRide ride) {
        return rideService.create(ride);
    }

    @Override
    public Page<RideDTO> findAll(RideFilterOption options) {
        return rideService.findAll(options);
    }

    @Override
    public RideDTO findById(Long id) {
        return rideService.findById(id);
    }

    @Override
    public Page<RideDTO> findDriverRides(Long driverId, RideFilterOption options) {
        return rideService.findDriverRides(driverId,options);
    }

    @Override
    public ResponseEntity<String> deleteById(Long rideId) {
        rideService.deleteById(rideId);
        return new ResponseEntity<>("RIDE DELETED", HttpStatus.OK);
    }
    @Override
    public RideDTO update(Long rideId, Map<String, Object> changes) {
        return rideService.update(rideId,changes);
    }
}
