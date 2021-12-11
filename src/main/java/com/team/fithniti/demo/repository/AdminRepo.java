package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Admin;
import com.team.fithniti.demo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Long> {
    Optional<Admin> findByUser(AppUser byId);
}
