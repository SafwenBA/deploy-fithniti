package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.RideReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RideReportRepo extends JpaRepository<RideReport, Long> {
    @Query("SELECT r FROM RideReport r where r.ride.driver.id = :driverId")
    Page<RideReport> findAllByDriver(UUID driverId, Pageable pageable);

    @Query("SELECT p FROM  RideReport p where p.reported.id = :passengerId")
    Page<RideReport> findAllByPassengerId(UUID passengerId, Pageable pageable);
}
