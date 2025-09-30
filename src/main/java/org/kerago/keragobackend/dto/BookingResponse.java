package org.kerago.keragobackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.kerago.keragobackend.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record BookingResponse(
        Long bookingId,
        String username,
        String hotelName,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate checkIn,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate checkOut,
        Status status,
        Integer guests,
        BigDecimal totalPrice,
        List<RoomResponse> room

) {
}
