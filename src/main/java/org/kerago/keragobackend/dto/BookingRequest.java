package org.kerago.keragobackend.dto;

import java.time.LocalDateTime;

public record BookingRequest(

     Long hotelId,
     LocalDateTime checkIn,
     LocalDateTime checkOut,
     Integer guests,
     Integer roomQuantity
) {
}


