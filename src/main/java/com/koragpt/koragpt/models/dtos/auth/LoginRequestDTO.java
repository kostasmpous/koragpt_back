package com.koragpt.koragpt.models.dtos.auth;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;

}