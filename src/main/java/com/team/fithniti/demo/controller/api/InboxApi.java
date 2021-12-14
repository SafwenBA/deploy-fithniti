package com.team.fithniti.demo.controller.api;

import com.team.fithniti.demo.dto.request.SendActionDTO;
import com.team.fithniti.demo.dto.response.MessageSentDTO;
import com.team.fithniti.demo.model.Inbox;
import com.twilio.http.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api
@RequestMapping("/inbox")
public interface InboxApi {

    @ApiOperation(value = "Send message to a user",responseContainer = "MessageSentDTO")
    @PutMapping
    ResponseEntity<MessageSentDTO> send(@RequestBody SendActionDTO sendActionDTO);

    @ApiOperation(value = "Get user inbox by id" ,responseContainer = "Inbox")
    @GetMapping("/{id}")
    ResponseEntity<Inbox> getInboxByUserId(@PathVariable UUID id);
}
