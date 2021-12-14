package com.team.fithniti.demo.repository;

import com.team.fithniti.demo.model.Inbox;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface InboxRepo extends MongoRepository<Inbox, String> {

    Optional<Inbox> findByUserId(UUID id);
}
