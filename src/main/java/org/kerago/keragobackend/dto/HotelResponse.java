package org.kerago.keragobackend.dto;

import org.kerago.keragobackend.model.Booking;

import java.math.BigDecimal;
import java.util.Set;

public record HotelResponse(
        String name,
        String location,
        BigDecimal rentPerNight,
        String description,
        Integer roomAvailability,
        Set<Booking> bookingList
) {
}
