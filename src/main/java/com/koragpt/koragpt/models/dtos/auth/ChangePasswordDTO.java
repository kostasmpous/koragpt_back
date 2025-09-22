package com.koragpt.koragpt.models.dtos.auth;

import lombok.Data;

@Data
public class ChangePasswordDTO {
    private String oldPassword;
    private String newPassword;
}
