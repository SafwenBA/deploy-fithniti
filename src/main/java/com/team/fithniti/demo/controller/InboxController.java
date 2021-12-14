package com.team.fithniti.demo.controller;

import com.team.fithniti.demo.controller.api.InboxApi;
import com.team.fithniti.demo.dto.request.SendActionDTO;
import com.team.fithniti.demo.dto.response.MessageSentDTO;
import com.team.fithniti.demo.model.Inbox;
import com.team.fithniti.demo.service.InboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class InboxController implements InboxApi {
    @Autowired
    private InboxService inboxService;
    @Override
    public ResponseEntity<MessageSentDTO> send(SendActionDTO sendActionDTO) {
        MessageSentDTO send = inboxService.send(sendActionDTO);

        if(send.getMessage() == "sent!"){
            return new ResponseEntity<>(send, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(send, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Inbox> getInboxByUserId(UUID id) {
        Inbox inboxByUserId = inboxService.getInboxByUserId(id);

        if(inboxByUserId != null){
            return new ResponseEntity<>(inboxByUserId, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
