package com.koragpt.koragpt.models.dtos.chat;

import com.koragpt.koragpt.models.Message;
import lombok.Data;

import java.util.List;
@Data
public class AllChatDTO {
    private Long chatId;
    private List<MessageAllChatDTO> text;
}
