package org.kerago.keragobackend.service;

import org.kerago.keragobackend.dto.RoomResponse;
import org.kerago.keragobackend.model.Rooms;
import org.springframework.stereotype.Service;

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
}
