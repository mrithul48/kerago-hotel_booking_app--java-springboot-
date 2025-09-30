package org.kerago.keragobackend.dto;

import org.kerago.keragobackend.model.Booking;

import java.math.BigDecimal;
import java.util.Set;

public record HotelResponse(
        Long hotelId,
        String name,
        String location,
        String description,
        Set<RoomResponse> room

) {
}
