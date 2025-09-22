package com.koragpt.koragpt.services;

import com.koragpt.koragpt.models.Message;
import com.koragpt.koragpt.models.ModelsAI;
import com.koragpt.koragpt.models.dtos.openai.requests.CompletionRequestDTO;
import com.koragpt.koragpt.models.dtos.openai.requests.MessageDTO;
import com.koragpt.koragpt.models.dtos.openai.responses.ConversationAIResponse;
import com.koragpt.koragpt.repositories.ModelsAIRepository;
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
    private String OpenAIapiKey;
    private String GeminiApiKey;
    private String urlOpenAI = "https://api.openai.com/v1/chat/completions";
    private String urlGemini = "https://generativelanguage.googleapis.com/v1beta/openai/chat/completions";
    private ModelsAIRepository modelsAIRepository;

    public CompletionApiService(@Value("${llms.openai.key}") String OpenAIapiKey, @Value("${llms.gemini.key}") String GeminiApiKey, ModelsAIRepository modelsAIRepository) {
        this.OpenAIapiKey = OpenAIapiKey;
        this.GeminiApiKey = GeminiApiKey;
        this.modelsAIRepository = modelsAIRepository;
    }

    public String createMessageToLLM(String model, List<Message> m) {
        String company = modelsAIRepository.findByModel(model).get(0).getCompany();
        RestTemplate restTemplate = new RestTemplate();
        String apiKey = GeminiApiKey;
        String url = urlGemini;
        if(company.equals("OpenAI")){
            apiKey = OpenAIapiKey;
            url = urlOpenAI;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        List<MessageDTO> messages = generateMessages(m);
        CompletionRequestDTO requestBody = new CompletionRequestDTO(model,messages);


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
