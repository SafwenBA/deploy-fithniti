package com.team.fithniti.demo.service.impl;

import com.team.fithniti.demo.dto.request.SendActionDTO;
import com.team.fithniti.demo.dto.response.MessageSentDTO;
import com.team.fithniti.demo.model.AppUser;
import com.team.fithniti.demo.model.Inbox;
import com.team.fithniti.demo.model.Message;
import com.team.fithniti.demo.repository.InboxRepo;
import com.team.fithniti.demo.repository.UserRepo;
import com.team.fithniti.demo.service.InboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InboxServiceImpl implements InboxService {
    @Autowired
    private InboxRepo inboxRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public MessageSentDTO send(SendActionDTO sendActionDTO) {
        Optional<Inbox> inbox = inboxRepo.findByUserId(sendActionDTO.getReceiverId());
        Optional<AppUser> appUser = userRepo.findById(sendActionDTO.getSenderId()) ;
        if(inbox.isPresent() && appUser.isPresent()){
            Inbox inboxToUpdate = inbox.get();

            Message message = Message.builder()
                    .uuid(UUID.randomUUID())
                    .senderId(sendActionDTO.getSenderId())
                    .messageDate(LocalDate.now())
                    .content(sendActionDTO.getContent())
                    .senderName(appUser.get().getFirstName()+" "+appUser.get().getLastName())
                    .senderLogoUrl(appUser.get().getPhotoUrl())
                    .build();

            List<Message> messagesToUpdate = inboxToUpdate.getMessages();

            messagesToUpdate.add(message);

            inboxToUpdate.setMessages(messagesToUpdate);

            inboxToUpdate.setNumberOfMessages(inboxToUpdate.getNumberOfMessages() + 1);

            //inboxToUpdate.setId(inbox.get().getId());

            inboxRepo.save(inboxToUpdate);

            return MessageSentDTO.builder().message("sent!").build();
        }else{
            return MessageSentDTO.builder().message("User not found!").build();
        }
    }

    @Override
    public Inbox getInboxByUserId(UUID userId) {
        Optional<Inbox> inbox = inboxRepo.findByUserId(userId);

        if(inbox.isPresent()){
            return inbox.get();
        }else{
            return  null;
        }
    }
}
