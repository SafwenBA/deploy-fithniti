package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DriverRepo extends JpaRepository<Driver, Long> {
    Optional<Driver> findByUser(AppUser user);

    Page<Driver> getDriversByReportsCountGreaterThan(int reportCounter, Pageable pageable);
}
