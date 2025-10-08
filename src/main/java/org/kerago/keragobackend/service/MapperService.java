package org.kerago.keragobackend.service;

import org.kerago.keragobackend.dto.ImageResponse;
import org.kerago.keragobackend.dto.RoomResponse;
import org.kerago.keragobackend.dto.adminDTO.RoomAdminRequest;
import org.kerago.keragobackend.exception.ResourceNotFoundException;
import org.kerago.keragobackend.model.Images;
import org.kerago.keragobackend.model.Rooms;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MapperService {

    // Convert single Room entity to RoomResponse
    public RoomResponse mapToRoomResponse(Rooms room) {
        return new RoomResponse(
                room.getId(),
                room.getRoomTypes(),
                room.getPricePerNight(),
                room.getRoomAvailableQuantity()
        );
    }

    // Convert Set of Rooms to List of RoomResponse
    public Set<RoomResponse> mapToRoomResponseList(Set<Rooms> rooms) {
        return rooms.stream()
                .map(this::mapToRoomResponse)
                .collect(Collectors.toSet());
    }

    public List<ImageResponse> mapToImageResponseList(List<Images> imagesList) {
        return imagesList.stream().map(images -> new ImageResponse(images.getId(),images.getUrl())).toList();

    }


    public Set<RoomAdminRequest> mapToRoomAdminRequest(Set<Rooms> rooms){
        return rooms.stream().map(room->new RoomAdminRequest(
                room.getRoomTypes(),
                room.getRoomAvailableQuantity(),
                room.getPricePerNight()
        )).collect(Collectors.toSet());
    }

    public Long calculateStayDuration(LocalDate checkIn,LocalDate checkout){
        if(checkout.isBefore(checkIn)){
            throw new ResourceNotFoundException("Check-out date cannot be before check-in date");
        }
        return ChronoUnit.DAYS.between(checkIn,checkout);
    }
}
