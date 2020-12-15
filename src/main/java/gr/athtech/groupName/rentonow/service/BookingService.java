package gr.athtech.groupName.rentonow.service;

import gr.athtech.groupName.rentonow.dto.BookingDTO;
import gr.athtech.groupName.rentonow.exception.BadRequestException;
import gr.athtech.groupName.rentonow.exception.NotFoundException;
import gr.athtech.groupName.rentonow.model.Booking;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface BookingService {

    Page<Booking> getBookings(Long guestId, Long propertyId, LocalDate fromDate, LocalDate toDate) throws BadRequestException;
    BookingDTO getBookingById(Long id) throws NotFoundException;
    BookingDTO createBooking(BookingDTO bookingDTO);
    BookingDTO updateBooking(BookingDTO bookingDTO);

}
