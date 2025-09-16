package com.koragpt.koragpt.controllers;

import com.koragpt.koragpt.models.Message;
import com.koragpt.koragpt.models.dtos.CreateMessageRequestDTO;
import com.koragpt.koragpt.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService MessageService;

    public MessageController(MessageService messageService) {
        MessageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody CreateMessageRequestDTO msg) {
        return ResponseEntity.ok(MessageService.createMessage(msg));
    }
}
