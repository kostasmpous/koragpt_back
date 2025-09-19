package com.koragpt.koragpt.controllers;

import com.koragpt.koragpt.models.Chat;
import com.koragpt.koragpt.models.dtos.chat.AllChatDTO;
import com.koragpt.koragpt.models.dtos.CreateChatRequestDTO;
import com.koragpt.koragpt.models.dtos.Responses.ChatsExctractDTO;
import com.koragpt.koragpt.services.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/users/{id}/chats")
    public ResponseEntity<List<ChatsExctractDTO>> getAllChatsOfUser(@PathVariable Long id){
        return ResponseEntity.ok(chatService.getAllChatsUser(id));
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<AllChatDTO> getChat(@PathVariable Long chatId){
        return ResponseEntity.ok(chatService.getAllContentOfChat(chatId));
    }
}
