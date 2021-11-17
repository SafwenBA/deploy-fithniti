package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PassengerRepo extends JpaRepository<Passenger,Long> {
    List<RideRequest> findAllById(UUID passengerId, Pageable pageable);

    Page<Passenger> getPassengersByReportsCountGreaterThan(int reportCount, Pageable pageable);

    Optional<Passenger> findByUser(AppUser user);
}
