package gr.athtech.groupName.rentonow.controllers;

import gr.athtech.groupName.rentonow.dtos.*;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.exceptions.UploadException;
import gr.athtech.groupName.rentonow.models.Image;
import gr.athtech.groupName.rentonow.models.Property;
import gr.athtech.groupName.rentonow.services.AvailabilityService;
import gr.athtech.groupName.rentonow.services.BookingService;
import gr.athtech.groupName.rentonow.services.ImageService;
import gr.athtech.groupName.rentonow.services.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@RestController()
@RequestMapping("/properties")
public class PropertyController {

    @Autowired
    PropertyService service;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private AvailabilityService availabilityService;

    @GetMapping("/")
    public List<PropertyDto> getProperties(FindPropertyDto searchParams) {
        return service.findProperties(searchParams);
    }

    @GetMapping("/{id}")
    public PropertyDto getProperty(@PathVariable Long id) throws NotFoundException {
        Property property = service.findPropertyById(id);
        return PropertyDto.fromProperty(property);
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
    public BookingDto createBooking(@PathVariable Long id, @RequestBody ClosedOrBookedDatesDto closedOrBookedDatesDto)
            throws NotFoundException, BadRequestException {
        return bookingService.createBooking(closedOrBookedDatesDto, id);
    }

    @PostMapping("/{id}/closed")
    public AvailabilityDto setClosed(@PathVariable Long id, @RequestBody ClosedOrBookedDatesDto closedOrBookedDatesDto)
            throws BadRequestException, NotFoundException {
        return availabilityService.setClosed(id, closedOrBookedDatesDto);
    }

    @PostMapping("/{id}/images")
    public Image handleImageUpload(@PathVariable Long id, @RequestParam("file") MultipartFile file)
            throws NotFoundException, BadRequestException, UploadException {
        return imageService.storeImage(id, file);
    }

    @DeleteMapping("/{id}/images/{imageId}")
    public String handleImageDelete(@PathVariable Long id, @PathVariable Long imageId)
            throws NotFoundException, BadRequestException, UploadException {
        return imageService.destroyImage(id, imageId);
    }
}
