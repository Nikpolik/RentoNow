package gr.athtech.groupName.rentonow.controllers;

import java.util.List;

import gr.athtech.groupName.rentonow.dtos.*;
import gr.athtech.groupName.rentonow.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import gr.athtech.groupName.rentonow.services.PropertyService;

@RestController()
@RequestMapping("/properties")
public class PropertyRestController {

    @Autowired
    PropertyService service;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/")
    public List<PropertyDto> getProperties(FindPropertyDto searchParams) {
        return service.findProperties(searchParams);
    }

    @GetMapping("/{id}")
    public String getProperty() {
        return "getProperty";
    }

    @PostMapping("/")
    public CreatePropertyDto createProperty(@RequestBody CreatePropertyDto propertyDto) {
        return service.saveProperty(propertyDto);
    }
    @PostMapping("/{id}/bookings")
    public BookingDto createBooking(@PathVariable Long id, @RequestBody CreateBookingDto createBookingDto) {
        return bookingService.createBooking(createBookingDto, id);
    }
}
