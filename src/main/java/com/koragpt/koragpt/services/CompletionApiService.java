package com.koragpt.koragpt.services;

import com.koragpt.koragpt.models.Message;
import com.koragpt.koragpt.models.dtos.openai.requests.CompletionRequestDTO;
import com.koragpt.koragpt.models.dtos.openai.requests.MessageDTO;
import com.koragpt.koragpt.models.dtos.openai.responses.ConversationAIResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompletionApiService {
    private String apiKey;
    private String url = "https://api.openai.com/v1/chat/completions";

    public CompletionApiService(@Value("${llms.openai.key}") String apiKey) {
        this.apiKey = apiKey;
    }

    public String createMessageToLLM(String model, List<Message> m) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        List<MessageDTO> messages = generateMessages(m);
        CompletionRequestDTO requestBody = new CompletionRequestDTO(model,messages,120);


        HttpEntity<CompletionRequestDTO> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<ConversationAIResponse> response = restTemplate.postForEntity(
                url,
                request,
                ConversationAIResponse.class
        );

        ConversationAIResponse body = response.getBody();
        if (body == null || body.getId() == null || body.getId().isBlank()) {
            throw new IllegalStateException("Conversation response missing id");
        }
        return  body.getChoices().get(0).getMessage().getContent();
    }

    public List<MessageDTO> generateMessages(List<Message> m){
        List<MessageDTO> result = new ArrayList<>();
        // Add system message
        MessageDTO system = new MessageDTO();
        system.setRole("system");
        system.setContent("You try to be concise, short, and friendly");
        result.add(system);

        for(Message msg:m){
            MessageDTO msgDTO = new MessageDTO();
            msgDTO.setRole(msg.getUser().getRole());
            msgDTO.setContent(msg.getMessage());
            result.add(msgDTO);
        }
        return result;
    }

}
