package gr.athtech.groupName.rentonow.controllers;

import gr.athtech.groupName.rentonow.dtos.BookingDto;
import gr.athtech.groupName.rentonow.dtos.FindBookingDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Booking;
import gr.athtech.groupName.rentonow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "bookings")
public class BookingRestController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public Page<Booking> getBookings(@RequestParam(name = "num", required = false) Integer num,
                                     @RequestParam(name = "size", required = false) Integer size,
                                     @RequestParam(name = "sortBy", required = false) String sortBy,
                                     @RequestParam(name = "direction", required = false) String direction,
                                     @RequestBody FindBookingDto findBookingDto) throws BadRequestException {
        return bookingService.getBookings(num, size, sortBy, direction, findBookingDto);
    }

    @GetMapping(value = "/{id}")
    public BookingDto getBookingById(@PathVariable Long id) throws NotFoundException {
        return bookingService.getBookingById(id);
    }

    @DeleteMapping(value = "/{id}")
    public void cancelBooking(@PathVariable Long id) throws NotFoundException, BadRequestException {
        bookingService.deleteBooking(id);
    }

}
