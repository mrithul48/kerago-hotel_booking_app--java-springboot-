package org.kerago.keragobackend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;


import java.time.LocalDate;
import java.util.List;


public record BookingRequest(
        Long hotelId,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate checkIn,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate checkOut,
        Integer guests,
        List<RoomRequest> room

) {
}


