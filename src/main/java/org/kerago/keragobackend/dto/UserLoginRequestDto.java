package org.kerago.keragobackend.dto;

public record UserLoginRequestDto(
        String username,
        String password
) {
}
