package gr.athtech.groupName.rentonow.controllers;

import gr.athtech.groupName.rentonow.dtos.BookingDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Booking;
import gr.athtech.groupName.rentonow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(value = "bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public Page<Booking> getBookings(@RequestParam(name = "guestId", required = false) Long guestId,
                                     @RequestParam(name = "propertyId", required = false) Long propertyId,
                                     @RequestParam(name = "from", required = false) LocalDate fromDate,
                                     @RequestParam(name = "to", required = false) LocalDate toDate) throws BadRequestException {
        return bookingService.getBookings(guestId, propertyId, fromDate, toDate);
    }

    @GetMapping(value = "/{id}")
    public BookingDto getBookingById(@PathVariable Long id) throws NotFoundException {
        return bookingService.getBookingById(id);
    }

}
