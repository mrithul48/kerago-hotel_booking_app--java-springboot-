package org.kerago.keragobackend.repository;

import org.kerago.keragobackend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {
    @Query("SELECT COALESCE(SUM(r.roomBookingQuantity), 0) FROM Booking b JOIN b.bookedRooms r " +
            "WHERE b.hotel.id = :hotelId AND b.status IN ('PENDING','CONFIRMED') AND " +
            "(:checkIn < b.checkOut AND :checkOut > b.checkIn) AND r.roomTypes = :roomType")
    Integer countBookedRoomsForTypeAndDates(@Param("hotelId") Long hotelId,
                                            @Param("roomType") org.kerago.keragobackend.enums.RoomTypes roomType,
                                            @Param("checkIn") java.time.LocalDate checkIn,
                                            @Param("checkOut") java.time.LocalDate checkOut);
}
