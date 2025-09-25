package org.kerago.keragobackend.service;


import org.kerago.keragobackend.dto.HotelRequest;
import org.kerago.keragobackend.dto.HotelResponse;
import org.kerago.keragobackend.exception.ResourceNotFoundException;
import org.kerago.keragobackend.model.Hotel;
import org.kerago.keragobackend.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HotelService {
    @Autowired
    HotelRepository hotelRepository;


    public HotelResponse hotelRegister(HotelRequest hotelRequest) {
        Hotel hotel = new Hotel();
        hotel.setName(hotelRequest.name());
        hotel.setLocation(hotelRequest.location());
        hotel.setRentPerNight(hotelRequest.rentPerNight());
        hotel.setDescription(hotelRequest.description());
        hotel.setRoomAvailability(hotelRequest.roomAvailability());
        hotel.setCreatedAt(LocalDateTime.now());
        Hotel newHotel = hotelRepository.save(hotel);

        return new HotelResponse(
                newHotel.getName(),
                newHotel.getLocation(),
                newHotel.getRentPerNight(),
                newHotel.getDescription(),
                newHotel.getRoomAvailability(),
                newHotel.getBookingList()
        );
    }

    public List<HotelResponse> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream().map(hotel -> new HotelResponse(
                hotel.getName(),
                hotel.getLocation(),
                hotel.getRentPerNight(),
                hotel.getDescription(),
                hotel.getRoomAvailability(),
                hotel.getBookingList()
        )).toList();
    }

    public HotelResponse getHotelById(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("hotel not found"));
        return new HotelResponse(
                hotel.getName(),
                hotel.getLocation(),
                hotel.getRentPerNight(),
                hotel.getDescription(),
                hotel.getRoomAvailability(),
                hotel.getBookingList()
        );
    }

    public HotelResponse updateHotel(Long id, HotelRequest hotelRequest) {
        return hotelRepository.findById(id)
                .map(hotel -> {
                    hotel.setName(hotelRequest.name());
                    hotel.setLocation(hotelRequest.location());
                    hotel.setDescription(hotelRequest.description());
                    hotel.setRoomAvailability(hotelRequest.roomAvailability());
                    hotel.setRentPerNight(hotelRequest.rentPerNight());
                    hotelRepository.save(hotel);

                    return new HotelResponse(
                            hotel.getName(),
                            hotel.getLocation(),
                            hotel.getRentPerNight(),
                            hotel.getDescription(),
                            hotel.getRoomAvailability(),
                            hotel.getBookingList()
                    );
                }).orElseThrow(() -> new ResourceNotFoundException("hotel not found"));
    }

    public HotelResponse deleteHotel(Long id) {
        Hotel hotel = hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("hotel not found"));
        hotelRepository.delete(hotel);

        return new HotelResponse(
                hotel.getName(),
                hotel.getLocation(),
                hotel.getRentPerNight(),
                hotel.getDescription(),
                hotel.getRoomAvailability(),
                hotel.getBookingList()
        );
    }
}

