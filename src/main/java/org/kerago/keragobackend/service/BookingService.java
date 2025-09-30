package org.kerago.keragobackend.service;

import org.kerago.keragobackend.dto.BookingRequest;
import org.kerago.keragobackend.dto.BookingResponse;
import org.kerago.keragobackend.dto.RoomRequest;
import org.kerago.keragobackend.dto.RoomResponse;
import org.kerago.keragobackend.enums.Status;
import org.kerago.keragobackend.exception.ResourceNotFoundException;
import org.kerago.keragobackend.model.Booking;
import org.kerago.keragobackend.model.Hotel;
import org.kerago.keragobackend.model.Rooms;
import org.kerago.keragobackend.model.Users;
import org.kerago.keragobackend.repository.BookingRepository;
import org.kerago.keragobackend.repository.HotelRepository;
import org.kerago.keragobackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    UserRepository userRepository;

    public BookingResponse createBooking(BookingRequest bookingRequest, Authentication authentication) {

        // Fetch hotel
        Hotel hotel = hotelRepository.findById(bookingRequest.hotelId())
                .orElseThrow(() -> new ResourceNotFoundException("hotel not found"));

        // Fetch user
        Users users = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("user not found"));

        // Check hotel availability
        boolean unAvailable = hotel.getRooms().stream().allMatch(rooms -> rooms.getRoomAvailableQuantity() <= 0);

        if (unAvailable) {
            throw new ResourceNotFoundException("no room available in hotel");
        }

        // Create booking
        Booking booking = new Booking();
        booking.setCheckIn(bookingRequest.checkIn());
        booking.setCheckOut(bookingRequest.checkOut());
        booking.setGuests(bookingRequest.guests());
        booking.setHotel(hotel);
        booking.setUser(users);
        booking.setStatus(Status.CONFIRMED);

        // Prepare booked rooms and total price
        Set<Rooms> bookedRooms = new HashSet<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (RoomRequest roomRequest : bookingRequest.room()) {

            Rooms hotelRoom = hotel.getRooms()
                    .stream()
                    .filter(r -> r.getRoomTypes().equals(roomRequest.roomTypes()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("requested room not available"));

            if (hotelRoom.getRoomAvailableQuantity() < roomRequest.roomBookingQuantity()) {
                throw new ResourceNotFoundException("Not enough rooms available for type:" + roomRequest.roomTypes());
            }

            // Reduce hotel room quantity
            hotelRoom.setRoomAvailableQuantity(hotelRoom.getRoomAvailableQuantity() - roomRequest.roomBookingQuantity());


            // Create booked room
            Rooms bookedRoom = new Rooms();


            bookedRoom.setRoomBookingQuantity(roomRequest.roomBookingQuantity());
            bookedRoom.setRoomTypes(roomRequest.roomTypes());
            bookedRoom.setBooking(booking); // link to booking
            bookedRoom.setPricePerNight(hotelRoom.getPricePerNight());
            bookedRooms.add(bookedRoom);

            // Update total price
            totalPrice = totalPrice.add(hotelRoom.getPricePerNight()
                    .multiply(BigDecimal.valueOf(roomRequest.roomBookingQuantity())));

        }

        // Save updated hotel room quantities
        hotelRepository.save(hotel);

        // Set rooms and total price in booking
        booking.setBookedRooms(bookedRooms);
        booking.setTotalPrice(totalPrice);

        // Save booking
        Booking saveBooking = bookingRepository.save(booking);

        // Prepare room response
        List<RoomResponse> room = new ArrayList<>();
        for (Rooms r : saveBooking.getBookedRooms()) {
            RoomResponse roomResponse = new RoomResponse(
                    r.getId(),
                    r.getRoomTypes(),
                    r.getPricePerNight(),
                    r.getRoomBookingQuantity()
            );
            room.add(roomResponse);
        }

        // Return booking response
        return new BookingResponse(
                saveBooking.getId(),
                saveBooking.getUser().getUsername(),
                saveBooking.getHotel().getName(),
                saveBooking.getCheckIn(),
                saveBooking.getCheckOut(),
                saveBooking.getStatus(),
                saveBooking.getGuests(),
                saveBooking.getTotalPrice(),
                room
        );

    }
}
