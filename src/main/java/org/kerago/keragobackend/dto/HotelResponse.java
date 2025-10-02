package org.kerago.keragobackend.dto;


import org.kerago.keragobackend.dto.adminDTO.RoomAdminRequest;

import java.util.List;
import java.util.Set;

public record HotelResponse(
        Long hotelId,
        String name,
        String location,
        String description,
        Set<RoomAdminRequest> room,
        List<ImageResponse> imageList

) {
}
