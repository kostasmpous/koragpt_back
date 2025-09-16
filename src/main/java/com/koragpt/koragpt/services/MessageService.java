package com.koragpt.koragpt.services;

import com.koragpt.koragpt.models.Chat;
import com.koragpt.koragpt.models.Message;
import com.koragpt.koragpt.models.User;
import com.koragpt.koragpt.models.dtos.CreateMessageRequestDTO;
import com.koragpt.koragpt.repositories.ChatRepository;
import com.koragpt.koragpt.repositories.MessageRepository;
import com.koragpt.koragpt.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository MessageReposity;

    public MessageService(UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageReposity) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        MessageReposity = messageReposity;
    }

    public  Message createMessage(CreateMessageRequestDTO msg) {
        Message message = new Message();
        User user = userRepository.findUserById(msg.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Chat chat = chatRepository.findById(msg.getChatId()).orElseThrow(() -> new RuntimeException("User not found"));

        message.setChat(chat);
        message.setUser(user);
        message.setMessage(msg.getText());

        return this.MessageReposity.save(message);
    }
}
