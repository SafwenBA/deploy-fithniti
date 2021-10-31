package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Ride;
import com.team.fithniti.demo.model.RideRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RideRepo extends JpaRepository<Ride,Long> {
}
