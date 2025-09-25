package org.kerago.keragobackend.controller;


import jakarta.validation.Valid;
import org.kerago.keragobackend.dto.HotelRequest;
import org.kerago.keragobackend.dto.HotelResponse;
import org.kerago.keragobackend.exception.ResourceNotFoundException;
import org.kerago.keragobackend.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/hotels")
public class HotelController {

    @Autowired
    HotelService hotelService;

    @PostMapping
    public ResponseEntity<HotelResponse> hotelRegister(@Valid @RequestBody HotelRequest hotelRequest) {
        HotelResponse hotelResponse = hotelService.hotelRegister(hotelRequest);
        if (hotelResponse != null) {
            return new ResponseEntity<>(hotelResponse, HttpStatus.CREATED);
        }
        throw new ResourceNotFoundException("hotel registered UNSUCCESSFULLY");
    }

    @GetMapping
    public ResponseEntity<List<HotelResponse>> getAllHotels() {
        return ResponseEntity.ok(hotelService.getAllHotels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getHotelById(@PathVariable Long id) {
        HotelResponse hotelResponse = hotelService.getHotelById(id);
        return new ResponseEntity<>(hotelResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HotelResponse> updateHotel(@PathVariable Long id,@RequestBody HotelRequest hotelRequest) {
      HotelResponse hotelResponse = hotelService.updateHotel(id,hotelRequest);
      return new ResponseEntity<>(hotelResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HotelResponse> deleteHotel(@PathVariable Long id){
      return ResponseEntity.ok( hotelService.deleteHotel(id));
    }
}
