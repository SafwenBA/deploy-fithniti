package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.dto.response.RideDTO;
import com.team.fithniti.demo.model.Driver;
import com.team.fithniti.demo.model.Passenger;
import com.team.fithniti.demo.model.PassengerReview;
import com.team.fithniti.demo.model.Ride;
import com.team.fithniti.demo.repository.DriverRepo;
import com.team.fithniti.demo.repository.PassengerRepo;
import com.team.fithniti.demo.repository.RideRepo;
import com.team.fithniti.demo.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private PassengerRepo passengerRepo;

    @Override
    public Page<Ride> getMyPools(Long driverId, Pageable pageable) {
        boolean present = driverRepo.findById(driverId).isPresent();
        if(present){
            return (Page<Ride>) driverRepo.findById(driverId).get().getRides().stream().map(ride -> RideDTO.fromEntity(ride));
        }else{
            //TODO: Fix Exception
            throw new IllegalStateException("Driver not found");
        }
    }

    //TODO : change with DTO
    @Override
    public PassengerReview createReview(PassengerReview passengerReview, Long passengerId) {
//        //Assigning the review to the concurrent passenger
//        passengerRepo.findById(passengerId).ifPresentOrElse(passenger -> {
//            List<PassengerReview> updatedPassengerReviews = Stream.concat(
//                    passenger.getPassengerReviews().stream(), Stream.of(passengerReview)
//            ).collect(Collectors.toList());
//            passenger.setPassengerReviews(updatedPassengerReviews);
//            passengerRepo.save(passenger);
//            // TODO: Change exception after implementation
//        }, () -> { throw new IllegalStateException("Passenger not found"); });
//
//        return passengerReview;
        return null;
    }

}
