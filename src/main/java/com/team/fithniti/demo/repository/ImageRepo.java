package com.team.fithniti.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface ImageRepo extends MongoRepository<Image, String> {
    Optional<Image> findByUserId(UUID id);
    void deleteByUserId(UUID id);
}
