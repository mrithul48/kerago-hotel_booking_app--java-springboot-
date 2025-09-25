package org.kerago.keragobackend.dto;



import java.util.Set;

public record UserRequest(
        Long userId,
        Set<BookingRequest> bookings
) {
}
