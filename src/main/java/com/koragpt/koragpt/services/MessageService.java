package com.koragpt.koragpt.services;

import com.koragpt.koragpt.models.Chat;
import com.koragpt.koragpt.models.Message;
import com.koragpt.koragpt.models.User;
import com.koragpt.koragpt.models.dtos.CreateMessageRequestDTO;
import com.koragpt.koragpt.repositories.ChatRepository;
import com.koragpt.koragpt.repositories.MessageRepository;
import com.koragpt.koragpt.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MessageRepository MessageRepository;
    private final CompletionApiService completionApiService;

    public MessageService(UserRepository userRepository, ChatRepository chatRepository, MessageRepository messageReposity, CompletionApiService completionApiService) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.MessageRepository = messageReposity;
        this.completionApiService = completionApiService;
    }

    public  Message createMessage(CreateMessageRequestDTO msg, List<Message> m, String model) {
        Message message = new Message();
        User user = userRepository.findUserById(msg.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Chat chat = chatRepository.findById(msg.getChatId()).orElseThrow(() -> new RuntimeException("Chat not found"));
        User assistantUser = userRepository.findUserByUsername("assistant").orElseThrow(() -> new RuntimeException("User not found"));

        message.setChat(chat);
        message.setUser(user);
        message.setMessage(msg.getText());
        chat.setTitle(msg.getText());
        chatRepository.save(chat);//update preview of chat with latest message
        MessageRepository.save(message);

        m.add(message);
        String answer = completionApiService.createMessageToLLM(model,m);

        Message answerMessage = new Message();
        answerMessage.setChat(chat);
        answerMessage.setUser(assistantUser);
        answerMessage.setMessage(answer);
        MessageRepository.save(answerMessage);

        return answerMessage;
    }
}
