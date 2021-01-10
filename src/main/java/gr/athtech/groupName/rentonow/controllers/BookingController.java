package gr.athtech.groupName.rentonow.controllers;

import gr.athtech.groupName.rentonow.dtos.BookingDto;
import gr.athtech.groupName.rentonow.dtos.FindBookingDto;
import gr.athtech.groupName.rentonow.dtos.PaymentDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Booking;
import gr.athtech.groupName.rentonow.services.BookingService;
import gr.athtech.groupName.rentonow.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public Page<Booking> getBookings(@RequestParam(name = "num", required = false, defaultValue = "0") Integer num,
                                     @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                     @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
                                     @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
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

    @PostMapping(value = "/{id}/payment")
    public PaymentDto createPayment(@PathVariable Long id, @RequestBody PaymentDto paymentDto) throws NotFoundException {
        return paymentService.createPayment(paymentDto, id);
    }

}
