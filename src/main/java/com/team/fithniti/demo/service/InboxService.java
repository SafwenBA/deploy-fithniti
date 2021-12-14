package com.team.fithniti.demo.service;

import com.team.fithniti.demo.dto.request.SendActionDTO;
import com.team.fithniti.demo.dto.response.MessageSentDTO;
import com.team.fithniti.demo.model.Inbox;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.UUID;

public interface InboxService {
    MessageSentDTO send(SendActionDTO sendActionDTO);

    Inbox getInboxByUserId(UUID userId);


}
