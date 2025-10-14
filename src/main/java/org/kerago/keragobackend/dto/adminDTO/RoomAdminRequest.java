package org.kerago.keragobackend.dto.adminDTO;


import org.kerago.keragobackend.enums.RoomTypes;

import java.math.BigDecimal;

public record RoomAdminRequest(
        Long roomId,
        RoomTypes roomTypes,
        Integer roomAvailableQuantity,
        BigDecimal pricePerNight
) {
}
