package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Driver;
import com.team.fithniti.demo.model.Ride;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RideRepo extends JpaRepository<Ride,Long> {
    @Override
    void delete(Ride entity);
}
