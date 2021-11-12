package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Passenger;
import com.team.fithniti.demo.model.RideReport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideReportRepo extends JpaRepository<RideReport, Long> {
    List<RideReport> findAllByPassenger(Passenger passenger, Pageable pageable);//here i made an assumption that passenger is the person who made the report
}
