package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.dtos.BookingDTO;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Booking;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface BookingService {

    Page<Booking> getBookings(Long guestId, Long propertyId, LocalDate fromDate, LocalDate toDate) throws BadRequestException;
    BookingDTO getBookingById(Long id) throws NotFoundException;
    BookingDTO createBooking(BookingDTO bookingDTO);
    BookingDTO updateBooking(BookingDTO bookingDTO);

}
