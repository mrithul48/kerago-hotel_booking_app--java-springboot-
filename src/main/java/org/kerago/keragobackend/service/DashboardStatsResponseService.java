package org.kerago.keragobackend.service;

import org.kerago.keragobackend.dto.DashBoardDto;
import org.kerago.keragobackend.repository.BookingRepository;
import org.kerago.keragobackend.repository.HotelRepository;
import org.kerago.keragobackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardStatsResponseService {

    private final HotelRepository hotelRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public DashboardStatsResponseService(HotelRepository hotelRepository, UserRepository userRepository, BookingRepository bookingRepository) {
        this.hotelRepository = hotelRepository;
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    public DashBoardDto getStatus() {
        Long totalUsers = userRepository.count();
        Long totalHotel = hotelRepository.count();
        Long totalBooking = bookingRepository.count();

        Double totalRevenue = bookingRepository.findAll()
                .stream().mapToDouble(booking->booking.getTotalPrice() !=null ? booking.getTotalPrice().doubleValue() : 0).sum();

        return DashBoardDto.builder()
                .totalUser(totalUsers)
                .totalHotel(totalHotel)
                .totalBooking(totalBooking)
                .totalRevenue(totalRevenue)
                .build();
    }
}
