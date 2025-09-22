package com.koragpt.koragpt.models.dtos.auth;

import lombok.Data;

@Data
public class SignupRequestDTO {
    private String first_name;
    private String last_name;
    private String username;
    private String email;
    private String password;
    private String role;
    private boolean active;
}
