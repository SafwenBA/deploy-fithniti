package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Image;
import com.team.fithniti.demo.model.Logo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface ImageRepo extends MongoRepository<Image, String> {
    Optional<Image> findByUserId(UUID id);
    void deleteByUserId(UUID id);
}
