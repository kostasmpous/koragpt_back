package com.koragpt.koragpt.controllers;

import com.koragpt.koragpt.models.Chat;
import com.koragpt.koragpt.models.User;
import com.koragpt.koragpt.models.dtos.CreateChatRequestDTO;
import com.koragpt.koragpt.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ResponseEntity<Chat> createChat(@RequestBody CreateChatRequestDTO chat) {
        return ResponseEntity.ok(chatService.createChat(chat));
    }
}
