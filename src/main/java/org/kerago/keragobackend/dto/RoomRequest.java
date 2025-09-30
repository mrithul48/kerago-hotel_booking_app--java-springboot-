package org.kerago.keragobackend.dto;

import org.kerago.keragobackend.enums.RoomTypes;


public record RoomRequest(
        RoomTypes roomTypes,
        Integer roomBookingQuantity
) {
}
