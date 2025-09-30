package org.kerago.keragobackend.dto;

import org.kerago.keragobackend.enums.RoomTypes;

import java.math.BigDecimal;

public record RoomResponse(
        Long id,
        RoomTypes roomTypes,
        BigDecimal pricePerNight,
        Integer roomBookingQuantity



) {
}
