package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<AppUser, UUID> {
    Optional<AppUser> findByPhoneNumber(String phoneNumber);
    boolean existsAppUserByPhoneNumber(String phoneNumber) ;

    @Modifying
    @Query(value = "UPDATE AppUser set password = :newPassword where id = :userId")
    void updatePassword(String newPassword,UUID userId) ;

    @Modifying
    @Query(value = "UPDATE AppUser set photoUrl = :newLogo where id = :userId")
    void updateLogo(String newLogo,UUID userId) ;
}
