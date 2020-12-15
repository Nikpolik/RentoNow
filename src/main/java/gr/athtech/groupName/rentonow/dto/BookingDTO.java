package gr.athtech.groupName.rentonow.dto;

import gr.athtech.groupName.rentonow.model.Booking;
import gr.athtech.groupName.rentonow.model.Property;
import gr.athtech.groupName.rentonow.model.User;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookingDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private User guest;
    private Property property;

    public static Booking toBooking(BookingDTO bookingDTO) {
        Booking booking = new Booking();
        booking.setId(bookingDTO.getId());
        booking.setStartDate(bookingDTO.getStartDate());
        booking.setEndDate(bookingDTO.getEndDate());
        booking.setGuest(bookingDTO.getGuest());
        return booking;
    }

    public static BookingDTO fromBooking(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setStartDate(booking.getStartDate());
        bookingDTO.setEndDate(booking.getEndDate());
        bookingDTO.setGuest(booking.getGuest());
        return bookingDTO;
    }
}
