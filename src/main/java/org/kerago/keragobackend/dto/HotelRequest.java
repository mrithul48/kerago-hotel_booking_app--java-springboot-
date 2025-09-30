package org.kerago.keragobackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.kerago.keragobackend.dto.adminDTO.RoomAdminRequest;

import java.util.Set;

public record HotelRequest(

        @NotBlank(message = "Name cannot be blank")
        String name,
        @NotBlank(message = "Location cannot be blank")
        String location,
        @NotNull(message = "Rent per night is required")

        @NotBlank(message = "Description cannot be blank")
        String description,

        Set<RoomAdminRequest> room

) {
}
