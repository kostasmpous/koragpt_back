package com.koragpt.koragpt.models.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponseDTO {
    private String token;
    private String username;
    private Long id;
    private String role;
}