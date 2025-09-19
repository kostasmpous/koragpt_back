package com.koragpt.koragpt.models.dtos.openai.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompletionRequestDTO {
    private String model;
    private List<MessageDTO> messages;
    private Integer max_tokens;
}
