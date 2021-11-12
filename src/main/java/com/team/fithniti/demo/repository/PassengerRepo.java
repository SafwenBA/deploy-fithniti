package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Passenger;
import com.team.fithniti.demo.model.Ride;
import com.team.fithniti.demo.model.RideRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface PassengerRepo extends JpaRepository<Passenger,Long> {
    List<RideRequest> findAllById(UUID passengerId, Pageable pageable);
}
