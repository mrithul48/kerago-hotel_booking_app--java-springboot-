package org.kerago.keragobackend.dto;


import java.util.Set;

public record HotelResponse(
        Long hotelId,
        String name,
        String location,
        String description,
        Set<RoomResponse> room

) {
}
