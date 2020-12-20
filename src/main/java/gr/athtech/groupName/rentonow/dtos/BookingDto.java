package gr.athtech.groupName.rentonow.dtos;

import gr.athtech.groupName.rentonow.models.Booking;
import gr.athtech.groupName.rentonow.models.Property;
import gr.athtech.groupName.rentonow.models.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private User guest;
    private Property property;

    public static Booking toBooking(BookingDto bookingDto) {
        Booking booking = new Booking();
        booking.setId(bookingDto.getId());
        booking.setStartDate(bookingDto.getStartDate());
        booking.setEndDate(bookingDto.getEndDate());
        booking.setGuest(bookingDto.getGuest());
        return booking;
    }

    public static BookingDto fromBooking(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStartDate(booking.getStartDate());
        bookingDto.setEndDate(booking.getEndDate());
        bookingDto.setGuest(booking.getGuest());
        return bookingDto;
    }
}
