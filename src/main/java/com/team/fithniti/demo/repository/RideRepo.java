package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface RideRepo extends JpaRepository<Ride,Long>, QuerydslPredicateExecutor<Ride> {
    @Override
    void delete(Ride entity);
}
