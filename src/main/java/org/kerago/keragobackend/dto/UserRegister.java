package org.kerago.keragobackend.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRegister(
        @NotBlank(message = "username not blank")
        String username,
        @NotBlank(message = "email not blank")
        String email,
        @NotBlank(message = "password not blank")
        String password,
        @NotBlank(message = "phone number not blank")
        String phone
) {
}
