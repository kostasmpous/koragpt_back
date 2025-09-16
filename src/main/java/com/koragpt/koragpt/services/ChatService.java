package com.koragpt.koragpt.services;

import com.koragpt.koragpt.models.Chat;
import com.koragpt.koragpt.models.User;
import com.koragpt.koragpt.models.dtos.CreateChatRequestDTO;
import com.koragpt.koragpt.repositories.ChatRepository;
import com.koragpt.koragpt.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public Chat createChat(CreateChatRequestDTO chat){


        Chat createdChat = new Chat();
        User user = userRepository.findUserById(chat.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        createdChat.setUser(user);
        createdChat.setTitle("New Chat");

        return this.chatRepository.save(createdChat);
    }
}
