package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.model.Ride;
import com.team.fithniti.demo.repository.RideRepo;
import com.team.fithniti.demo.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideServiceImpl implements RideService {
    @Autowired
    private RideRepo rideRepo;

    @Override
    public Ride create(Ride ride) {
        // TODO: 10/29/21 check before if not exists ....
        return rideRepo.save(ride);
    }

    @Override
    public List<Ride> getAll() {
        return rideRepo.findAll();
    }

    @Override
    public List<Ride> getDriverRides(Long driverId) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        // TODO: 10/29/21 check before if not exists ....
        rideRepo.deleteById(id);
    }

    @Override
    public void update(Long rideId) {

    }

    @Override
    public void joinRide(Long rideID, Long passengerID) {

    }

    @Override
    public void acceptRequest(Long rideID) {

    }

    @Override
    public void rejectRequest(Long rideID) {

    }

    @Override
    public void cancelRequest(Long rideID, Long passengerID) {

    }
}
