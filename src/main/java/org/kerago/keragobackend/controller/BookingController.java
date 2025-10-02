package org.kerago.keragobackend.controller;

import org.kerago.keragobackend.dto.BookingCancelResponse;
import org.kerago.keragobackend.dto.BookingRequest;
import org.kerago.keragobackend.dto.BookingResponse;
import org.kerago.keragobackend.security.JwtUtil;
import org.kerago.keragobackend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest, Authentication authentication) {
        BookingResponse booking = bookingService.createBooking(bookingRequest, authentication);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<BookingCancelResponse> cancelBooking(@PathVariable Long bookingId, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.substring(7));
        BookingCancelResponse bookingCancelResponse = bookingService.cancelBooking(username,bookingId);
        return ResponseEntity.ok(bookingCancelResponse);
    }


}
