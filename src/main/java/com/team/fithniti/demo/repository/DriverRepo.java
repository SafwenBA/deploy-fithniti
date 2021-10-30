package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DriverRepo extends JpaRepository<Driver, Long> {
}
