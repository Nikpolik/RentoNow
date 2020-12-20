package gr.athtech.groupName.rentonow.dtos;

import gr.athtech.groupName.rentonow.models.Booking;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateBookingDto {
    private LocalDate startDate;
    private LocalDate endDate;

    public static Booking toBooking(CreateBookingDto createBookingDto) {
        Booking booking = new Booking();
        booking.setStartDate(createBookingDto.getStartDate());
        booking.setEndDate(createBookingDto.getEndDate());
        return booking;
    }
}


