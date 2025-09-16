package com.koragpt.koragpt.models.dtos;

import lombok.Data;

@Data
public class CreateMessageRequestDTO {
    private String text;
    private Long chatId;
    private Long userId;
}
