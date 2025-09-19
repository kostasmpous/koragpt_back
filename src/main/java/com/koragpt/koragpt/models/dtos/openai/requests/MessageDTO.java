package com.koragpt.koragpt.models.dtos.openai.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private String role;
    private String content;

}
