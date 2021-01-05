package gr.athtech.groupName.rentonow.controllers;

import gr.athtech.groupName.rentonow.dtos.*;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.services.BookingService;
import gr.athtech.groupName.rentonow.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public PropertyDto createProperty(@RequestBody CreatePropertyDto propertyDto) {
        return service.saveProperty(propertyDto);
    }

    @PutMapping("/")
    public PropertyDto updateProperty(@RequestBody CreatePropertyDto propertyDto) throws NotFoundException {
       return service.updateProperty(propertyDto);
    }

    @PostMapping("/{id}/bookings")
    public BookingDto createBooking(@PathVariable Long id, @RequestBody CreateBookingDto createBookingDto) throws NotFoundException, BadRequestException {
        return bookingService.createBooking(createBookingDto, id);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String notFoundExceptionHandler(Exception e) {
      return e.getMessage();
    }
    
}
