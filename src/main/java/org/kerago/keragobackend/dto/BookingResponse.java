package org.kerago.keragobackend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record BookingResponse(
        String hotelName,
        LocalDateTime checkIn,
        LocalDateTime checkOut,
        Integer guests,
        BigDecimal totalPrice
) {
}
