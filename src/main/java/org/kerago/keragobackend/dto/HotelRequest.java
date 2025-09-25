package org.kerago.keragobackend.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record HotelRequest(
        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotBlank(message = "Location cannot be blank")
        String location,
        @NotNull(message = "Rent per night is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Rent must be greater than 0")
        BigDecimal rentPerNight,
        @NotBlank(message = "Description cannot be blank")
        String description,
        @NotNull(message = "Room availability is required")
        Integer roomAvailability

) {
}
