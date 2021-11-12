package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Logo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface LogoRepo extends MongoRepository<Logo, String> {
    Optional<Logo> findByUserId(UUID id);
    void deleteByUserId(UUID id);
}
