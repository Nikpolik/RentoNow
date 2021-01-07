package gr.athtech.groupName.rentonow.controllers;

import gr.athtech.groupName.rentonow.dtos.*;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.services.AvailabilityService;
import gr.athtech.groupName.rentonow.services.BookingService;
import gr.athtech.groupName.rentonow.services.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController()
@RequestMapping("/properties")
public class PropertyRestController {

    @Autowired
    PropertyService service;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private AvailabilityService availabilityService;

    @GetMapping("/")
    public List<PropertyDto> getProperties(FindPropertyDto searchParams) {
        return service.findProperties(searchParams);
    }

    @GetMapping("/{id}")
    public String getProperty() {
        return "getProperty";
    }

    @PostMapping("/")
    public PropertyDto createProperty(@RequestBody CreatePropertyDto createDto) {
        return service.saveProperty(createDto);
    }

    @PutMapping("/")
    public PropertyDto updateProperty(@RequestBody UpdatePropertyDto propertyDto) throws NotFoundException {
        return service.updateProperty(propertyDto);
    }

    @PostMapping("/{id}/bookings")
    public BookingDto createBooking(@PathVariable Long id, @RequestBody CreateBookingDto createBookingDto)
            throws NotFoundException, BadRequestException {
        return bookingService.createBooking(createBookingDto, id);
    }

    @PostMapping("/{id}/closed")
    public AvailabilityDto setClosed(@PathVariable Long id, @RequestBody ClosedDatesDto closedDatesDto)
            throws BadRequestException, NotFoundException {
        return availabilityService.setClosed(id, closedDatesDto);
    }
}
