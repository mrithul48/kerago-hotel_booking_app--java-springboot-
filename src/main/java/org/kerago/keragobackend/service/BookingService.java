

package org.kerago.keragobackend.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.kerago.keragobackend.dto.*;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    @Autowired
    MapperService mapperService;

    @Transactional
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

        if (bookingRequest.room().stream().allMatch(r -> r.roomBookingQuantity() <= 0)) {
            throw new ResourceNotFoundException("booking quantity cannot be 0");
        }

        // Validate dates
        if (bookingRequest.checkIn() == null || bookingRequest.checkOut() == null) {
            throw new ResourceNotFoundException("check-in and check-out dates are required");
        }
        if (!bookingRequest.checkOut().isAfter(bookingRequest.checkIn())) {
            throw new ResourceNotFoundException("check-out must be after check-in");
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
        Long day = mapperService.calculateStayDuration(bookingRequest.checkIn(),bookingRequest.checkOut());
        for (RoomRequest roomRequest : bookingRequest.room()) {

            Rooms hotelRoom = hotel.getRooms()
                    .stream()
                    .filter(r -> r.getRoomTypes().equals(roomRequest.roomTypes()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("requested room not available"));

            // Calculate already booked rooms for the requested date range
            Integer alreadyBooked = bookingRepository.countBookedRoomsForTypeAndDates(
                    hotel.getId(),
                    roomRequest.roomTypes(),
                    bookingRequest.checkIn(),
                    bookingRequest.checkOut()
            );

            int availableForDates = hotelRoom.getRoomAvailableQuantity() - alreadyBooked;
            if (availableForDates < roomRequest.roomBookingQuantity()) {
                throw new ResourceNotFoundException("Not enough rooms available for type:" + roomRequest.roomTypes());
            }

            // Decrease available quantity for this room type immediately
            hotelRoom.setRoomAvailableQuantity(
                    hotelRoom.getRoomAvailableQuantity() - roomRequest.roomBookingQuantity()
            );

            // Create booked room
            Rooms bookedRoom = new Rooms();
            bookedRoom.setRoomBookingQuantity(roomRequest.roomBookingQuantity());
            bookedRoom.setRoomTypes(roomRequest.roomTypes());
            bookedRoom.setBooking(booking); // link to booking
            bookedRoom.setPricePerNight(hotelRoom.getPricePerNight());
            bookedRooms.add(bookedRoom);

            // Update total price
            totalPrice = totalPrice.add(hotelRoom.getPricePerNight()
                    .multiply(BigDecimal.valueOf(roomRequest.roomBookingQuantity()).multiply(BigDecimal.valueOf(day))));
        }

        // Set rooms and total price in booking
        booking.setBookedRooms(bookedRooms);
        booking.setTotalPrice(totalPrice);

        // Save booking
        Booking saveBooking = bookingRepository.save(booking);

        // Prepare mail variables AFTER booking is saved
        Map<String, Object> vars = new HashMap<>();
        vars.put("username", saveBooking.getUser().getUsername());
        vars.put("hotelName", saveBooking.getHotel().getName());
        vars.put("checkIn", saveBooking.getCheckIn().toString());
        vars.put("checkOut", saveBooking.getCheckOut().toString());
        vars.put("totalPrice", saveBooking.getTotalPrice().toString());

        List<Map<String, Object>> rooms = saveBooking.getBookedRooms().stream()
                .map(r -> {
                    Map<String, Object> m = new HashMap<>();
                    m.put("roomType", r.getRoomTypes());
                    m.put("qty", r.getRoomBookingQuantity());
                    m.put("price", r.getPricePerNight());
                    return m;
                }).toList();

        vars.put("rooms", rooms);
        vars.put("bookingUrl", "https://yourapp.com/bookings/" + saveBooking.getId());

        // Send HTML mail with Thymeleaf template
        mailService.sendHtmlMessage(
                saveBooking.getUser().getEmail(),
                "Booking Confirmed — Kerago",
                "booking-confirmation", // template under mail-templates/
                vars
        );

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


    public BookingCancelResponse cancelBooking(String username, Long bookingId) {
        Users users = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("username not found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()->new UsernameNotFoundException("booking not found"));

        if(!booking.getUser().getUsername().equals(username)){
            throw new UsernameNotFoundException("You are not allowed to cancel this booking");
        }

        for (Rooms bookedRoom:booking.getBookedRooms()){
            Hotel hotel = booking.getHotel();
            Rooms hotelRoom = hotel.getRooms()
                    .stream().filter(r->r.getRoomTypes().equals(bookedRoom.getRoomTypes()))
                    .findFirst()
                    .orElseThrow(()->new ResourceNotFoundException("room type not found in hotel"));
            hotelRoom.setRoomAvailableQuantity(
                    hotelRoom.getRoomAvailableQuantity() + bookedRoom.getRoomBookingQuantity()
            );
        }


        booking.setStatus(Status.CANCELLED);
        bookingRepository.save(booking);

        return new BookingCancelResponse(
                booking.getId(),
                booking.getUser().getUsername(),
                booking.getHotel().getName(),
                booking.getStatus()
        );

    }

    public List<BookingResponse> getAllBooking() {
        return bookingRepository.findAll().stream().filter(b -> b.getStatus() != Status.CANCELLED)
                .map(r ->
                        new BookingResponse(
                                r.getId(),
                                r.getUser().getUsername(),
                                r.getHotel().getName(),
                                r.getCheckIn(),
                                r.getCheckOut(),
                                r.getStatus(),
                                r.getGuests(),
                                r.getTotalPrice(),
                                r.getBookedRooms().stream().map(room -> new RoomResponse(
                                        room.getId(),
                                        room.getRoomTypes(),
                                        room.getPricePerNight(),
                                        room.getRoomBookingQuantity()
                                )).toList()
                        )
                ).toList();
    }

}

