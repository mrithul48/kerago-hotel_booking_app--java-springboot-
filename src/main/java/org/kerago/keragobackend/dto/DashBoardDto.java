package org.kerago.keragobackend.dto;

import lombok.Builder;

@Builder
public record DashBoardDto(
        Long totalUser,
        Long totalHotel,
        Long totalBooking,
        Double totalRevenue
) {

}
