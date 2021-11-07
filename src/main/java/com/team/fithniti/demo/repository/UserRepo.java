package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByPhoneNumber(String phoneNumber);
    boolean existsAppUserByPhoneNumber(String phoneNumber) ;
}
