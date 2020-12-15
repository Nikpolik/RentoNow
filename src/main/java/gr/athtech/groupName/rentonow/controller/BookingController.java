package gr.athtech.groupName.rentonow.controller;

import gr.athtech.groupName.rentonow.dto.BookingDTO;
import gr.athtech.groupName.rentonow.exception.BadRequestException;
import gr.athtech.groupName.rentonow.exception.NotFoundException;
import gr.athtech.groupName.rentonow.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping
    public List<BookingDTO> getBookings(@RequestParam(name = "guestId", required = false) Long guestId,
                                           @RequestParam(name = "propertyId", required = false) Long propertyId,
                                           @RequestParam(name = "from", required = false) LocalDate fromDate,
                                           @RequestParam(name = "to", required = false) LocalDate toDate) throws BadRequestException {
        return bookingService.getBookings(guestId, propertyId, fromDate, toDate);
    }

    @GetMapping(value = "/{id}")
    public BookingDTO getBookingById(@PathVariable Long id) throws NotFoundException {
        return bookingService.getBookingById(id);
    }

    @PostMapping
    public BookingDTO createBooking(@RequestBody BookingDTO bookingDTO) {
        return bookingService.createBooking(bookingDTO);
    }

}
