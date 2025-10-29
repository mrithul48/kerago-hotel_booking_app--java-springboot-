package org.kerago.keragobackend.dto;


import org.kerago.keragobackend.dto.adminDTO.RoomAdminRequest;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public record HotelResponse(
        Long hotelId,
        String name,
        String location,
        String description,
        Set<RoomAdminRequest> room,
        List<ImageResponse> imageList

) {
//    public HotelResponse(Long id, String name, String location, String description, Stream<RoomAdminRequest> roomAdminRequestStream) {
//        this.hotelId = id;
//        this.name = name;
//        this.location = location;
//        this.description = description;
//
//
//
//    }
}
