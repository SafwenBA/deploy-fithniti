package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Logo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface ImageRepo extends MongoRepository<Logo, UUID> {
}
