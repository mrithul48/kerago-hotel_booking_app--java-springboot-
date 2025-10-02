package org.kerago.keragobackend.service;


import jakarta.transaction.Transactional;
import org.kerago.keragobackend.dto.HotelRequest;
import org.kerago.keragobackend.dto.HotelResponse;
import org.kerago.keragobackend.dto.adminDTO.RoomAdminRequest;
import org.kerago.keragobackend.exception.ResourceNotFoundException;
import org.kerago.keragobackend.model.Hotel;
import org.kerago.keragobackend.model.Images;
import org.kerago.keragobackend.model.Rooms;
import org.kerago.keragobackend.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    private MapperService mapperService;


    // Create hotel with rooms
    public HotelResponse hotelRegister(HotelRequest hotelRequest, String url) throws IOException {

        Hotel hotel = new Hotel();

        hotel.setName(hotelRequest.name());
        hotel.setLocation(hotelRequest.location());
        hotel.setDescription(hotelRequest.description());
        hotel.setCreatedAt(LocalDateTime.now());

        Set<Rooms> rooms = new HashSet<>();
        for (RoomAdminRequest roomAdminRequest : hotelRequest.room()) {

            Rooms room = new Rooms();

            room.setRoomTypes(roomAdminRequest.roomTypes());
            room.setRoomAvailableQuantity(roomAdminRequest.roomAvailableQuantity());
            room.setPricePerNight(roomAdminRequest.pricePerNight());
            room.setHotel(hotel);// link room to hotel
            rooms.add(room);
        }

        hotel.setRooms(rooms);

        List<Images> imagesList = new ArrayList<>();
        Images images = new Images();
        images.setUrl(url);
        images.setHotel(hotel);  // important! link to hotel

        imagesList.add(images);

        hotel.setImagesList(imagesList);
        Hotel savedHotel = hotelRepository.save(hotel);

        return new HotelResponse(
                savedHotel.getId(),
                savedHotel.getName(),
                savedHotel.getLocation(),
                savedHotel.getDescription(),
                mapperService.mapToRoomAdminRequest(savedHotel.getRooms()),
                mapperService.mapToImageResponseList(savedHotel.getImagesList())

        );
    }

    @Transactional
    public List<HotelResponse> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream().map(hotel -> new HotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getLocation(),
                hotel.getDescription(),
                mapperService.mapToRoomAdminRequest(hotel.getRooms()),
                mapperService.mapToImageResponseList(hotel.getImagesList())

        )).toList();
    }

    public HotelResponse getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("hotel not found"));
        return new HotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getLocation(),
                hotel.getDescription(),
                mapperService.mapToRoomAdminRequest(hotel.getRooms()),
                mapperService.mapToImageResponseList(hotel.getImagesList())

        );
    }

    public HotelResponse updateHotel(Long id, HotelRequest hotelRequest) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.setName(hotelRequest.name());
                    hotel.setLocation(hotelRequest.location());
                    hotel.setDescription(hotelRequest.description());

                    for (RoomAdminRequest roomRequest : hotelRequest.room()) {
                        Rooms existingRoom = hotel.getRooms()
                                .stream()
                                .filter(r -> r.getRoomTypes().equals(roomRequest.roomTypes())
                                ).findFirst()
                                .orElse(null);

                        if (existingRoom != null) {
                            // update existing room
                            existingRoom.setRoomAvailableQuantity(roomRequest.roomAvailableQuantity());
                            existingRoom.setPricePerNight(roomRequest.pricePerNight());

                        } else {
                            // add new room
                            Rooms newRoom = new Rooms();
                            newRoom.setRoomTypes(roomRequest.roomTypes());
                            newRoom.setRoomAvailableQuantity(roomRequest.roomAvailableQuantity());
                            newRoom.setPricePerNight(roomRequest.pricePerNight());
                            newRoom.setHotel(hotel);

                            hotel.getRooms().add(newRoom);
                        }
                    }
                    hotelRepository.save(hotel);

                    return new HotelResponse(
                            hotel.getId(),
                            hotel.getName(),
                            hotel.getLocation(),
                            hotel.getDescription(),
                            mapperService.mapToRoomAdminRequest(hotel.getRooms()),
                            mapperService.mapToImageResponseList(hotel.getImagesList())

                    );
                }).orElseThrow(() -> new ResourceNotFoundException("hotel not found"));
    }

    public HotelResponse deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("hotel not found"));
        hotelRepository.delete(hotel);

        return new HotelResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getLocation(),
                hotel.getDescription(),
                mapperService.mapToRoomAdminRequest(hotel.getRooms()),
                mapperService.mapToImageResponseList(hotel.getImagesList())

        );
    }

}

