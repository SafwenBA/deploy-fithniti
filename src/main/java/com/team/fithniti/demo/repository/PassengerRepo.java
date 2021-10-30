package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepo extends JpaRepository<Passenger,Long> {
}
