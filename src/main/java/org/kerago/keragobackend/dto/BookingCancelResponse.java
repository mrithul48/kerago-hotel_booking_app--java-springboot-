package org.kerago.keragobackend.dto;

import org.kerago.keragobackend.enums.Status;

public record BookingCancelResponse(
        Long bookingId,
        String username,
        String hotelName,
        Status status
) {
}
