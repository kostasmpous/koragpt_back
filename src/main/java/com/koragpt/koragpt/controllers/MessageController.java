package com.koragpt.koragpt.controllers;

import com.koragpt.koragpt.models.Message;
import com.koragpt.koragpt.models.dtos.CreateMessageRequestDTO;
import com.koragpt.koragpt.repositories.ChatRepository;
import com.koragpt.koragpt.repositories.MessageRepository;
import com.koragpt.koragpt.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;
    private final MessageRepository msgRepository;

    public MessageController(MessageService messageService, ChatRepository chatRepository, MessageRepository msgRepository) {
        this.messageService = messageService;
        this.msgRepository = msgRepository;
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody CreateMessageRequestDTO msg, Authentication auth) {
        List<Message> m = msgRepository.findByChatIdOrderByCreatedAtAsc(msg.getChatId());
        return ResponseEntity.ok(messageService.createMessage(msg,m,msg.getModel(),auth));
    }
}
