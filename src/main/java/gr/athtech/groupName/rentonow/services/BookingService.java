package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.dtos.BookingDto;
import gr.athtech.groupName.rentonow.dtos.CreateBookingDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Booking;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface BookingService {

    Page<Booking> getBookings(Long guestId, Long propertyId, LocalDate fromDate, LocalDate toDate) throws BadRequestException;
    BookingDto getBookingById(Long id) throws NotFoundException;
    BookingDto createBooking(CreateBookingDto createBookingDto, Long propertyId);
    BookingDto updateBooking(BookingDto bookingDto);

}
