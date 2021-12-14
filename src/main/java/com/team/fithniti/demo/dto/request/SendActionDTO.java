package com.team.fithniti.demo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendActionDTO {
    private UUID senderId;
    private UUID receiverId;
    private String content;
}
