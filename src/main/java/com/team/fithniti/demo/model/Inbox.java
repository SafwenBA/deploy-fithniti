package com.team.fithniti.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "inboxes")
public class Inbox {

    @Id
    private String id;

    private UUID userId;

    private List<Message> messages;

    private long numberOfMessages;


}
