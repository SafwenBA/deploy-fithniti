package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.model.UserRecoveryRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRecoveryRequestRepo extends JpaRepository<UserRecoveryRequest,Long> {
    Optional<UserRecoveryRequest> findByRecoveryCode(String recoveryCode) ;
    Optional<UserRecoveryRequest> findByUser(AppUser user) ;
}
