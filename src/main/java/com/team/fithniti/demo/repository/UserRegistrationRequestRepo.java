package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.model.UserRegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRegistrationRequestRepo extends JpaRepository<UserRegistrationRequest,Long> {
    Optional<UserRegistrationRequest> findByUserId(UUID user_id) ;
}
