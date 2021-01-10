package gr.athtech.groupName.rentonow.dtos;

import gr.athtech.groupName.rentonow.models.Booking;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ToString
public class BookingDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private UserDto guest;
    private Long propertyId;
    private BigDecimal price;

    public static BookingDto fromBooking(Booking booking) {
        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(booking.getId());
        bookingDto.setStartDate(booking.getAvailability().getStartDate());
        bookingDto.setEndDate(booking.getAvailability().getEndDate());
        bookingDto.setGuest(UserDto.fromUser(booking.getGuest()));
        bookingDto.setPropertyId(booking.getProperty().getId());
        bookingDto.setPrice(booking.getPrice());
        return bookingDto;
    }
}
