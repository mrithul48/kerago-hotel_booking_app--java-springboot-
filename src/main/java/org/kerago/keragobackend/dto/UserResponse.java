package org.kerago.keragobackend.dto;

import org.kerago.keragobackend.enums.Role;
import org.kerago.keragobackend.model.Booking;

import java.util.Set;

public record UserResponse(
        String username,
        String email,
        Role role,
        Set<Booking> bookings
) {
}
