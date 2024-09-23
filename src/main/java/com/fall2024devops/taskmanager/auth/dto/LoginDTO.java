package com.fall2024devops.taskmanager.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LoginDTO {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTOInput {
        @NotBlank(message = "Email is mandatory")
        private String email;

        @NotBlank(message = "Password is mandatory")
        private String password;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginDTOOutput {
        private String accessToken;
    }
}
