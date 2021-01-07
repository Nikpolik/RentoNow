package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.dtos.ClosedOrBookedDatesDto;
import gr.athtech.groupName.rentonow.dtos.BookingDto;
import gr.athtech.groupName.rentonow.dtos.FindBookingDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Booking;
import org.springframework.data.domain.Page;


public interface BookingService {

    Page<Booking> getBookings(Integer num, Integer size, String sortBy, String direction, FindBookingDto findBookingDto) throws BadRequestException;
    BookingDto getBookingById(Long id) throws NotFoundException;
    BookingDto createBooking(ClosedOrBookedDatesDto closedOrBookedDatesDto, Long propertyId) throws NotFoundException, BadRequestException;
    void deleteBooking(Long id) throws NotFoundException, BadRequestException;

}
