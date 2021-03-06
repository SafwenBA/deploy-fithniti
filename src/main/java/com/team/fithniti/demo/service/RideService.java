package com.team.fithniti.demo.service;

import com.team.fithniti.demo.dto.request.NewRide;
import com.team.fithniti.demo.dto.response.RideDTO;
import com.team.fithniti.demo.dto.RideFilterOption;
import com.team.fithniti.demo.model.Ride;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface RideService {

    RideDTO create(NewRide ride);
    RideDTO findById(Long id);
    Ride findEntityById(Long id);
    Page<RideDTO> findAll(RideFilterOption options);
    Page<RideDTO> findDriverRides(Long driverId, RideFilterOption options);
    void deleteById(Long rideId);
    RideDTO update(Long rideId, Map<String,Object> changes);
}
