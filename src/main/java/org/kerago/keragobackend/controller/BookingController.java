package org.kerago.keragobackend.controller;

import org.kerago.keragobackend.dto.BookingRequest;
import org.kerago.keragobackend.dto.BookingResponse;
import org.kerago.keragobackend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest, Authentication authentication){
        BookingResponse booking = bookingService.createBooking(bookingRequest,authentication);
        return new ResponseEntity<>(booking,HttpStatus.CREATED);
    }
}
