package com.koragpt.koragpt.services;

import com.koragpt.koragpt.models.Chat;
import com.koragpt.koragpt.models.Message;
import com.koragpt.koragpt.models.User;
import com.koragpt.koragpt.models.dtos.chat.AllChatDTO;
import com.koragpt.koragpt.models.dtos.CreateChatRequestDTO;
import com.koragpt.koragpt.models.dtos.Responses.ChatsExctractDTO;
import com.koragpt.koragpt.models.dtos.chat.MessageAllChatDTO;
import com.koragpt.koragpt.repositories.ChatRepository;
import com.koragpt.koragpt.repositories.MessageRepository;
import com.koragpt.koragpt.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final CompletionApiService completionApiService;
    private final MessageRepository messageRepository;

    public ChatService(ChatRepository chatRepository, UserRepository userRepository, CompletionApiService completionApiService, MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
        this.completionApiService = completionApiService;
        this.messageRepository = messageRepository;
    }

    public Chat createChat(CreateChatRequestDTO chat){
        Chat createdChat = new Chat();
        User user = userRepository.findUserById(chat.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        createdChat.setUser(user);
        createdChat.setTitle("New Chat");
        return this.chatRepository.save(createdChat);
    }

    public List<ChatsExctractDTO> getAllChatsUser(Long id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        List<ChatsExctractDTO> response = chatRepository.findByUserOrderByUpdatedAtDesc(user).stream()
                .map(chat -> {
                    ChatsExctractDTO chatDTO = new ChatsExctractDTO();
                    chatDTO.setText(chat.getTitle());
                    chatDTO.setChatId(chat.getId());
                    return chatDTO;
                }).toList();

        return response;
    }

    public AllChatDTO getAllContentOfChat(Long chatId) {
        List<Message> messagesList = messageRepository.getMessageByChat(chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found")));
        AllChatDTO response = new AllChatDTO();
        List<MessageAllChatDTO> messages = new ArrayList<>();
        for(Message msg:messagesList){
            MessageAllChatDTO message = new MessageAllChatDTO();
            message.setMessage(msg.getMessage());
            message.setUser(msg.getUser().getFirst_name());
            messages.add(message);
        }
        response.setChatId(chatId);
        response.setText(messages);
        return response;
    }
}
