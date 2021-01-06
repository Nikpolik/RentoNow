package gr.athtech.groupName.rentonow.services.impl;

import gr.athtech.groupName.rentonow.models.AvailabilityStatus;
import gr.athtech.groupName.rentonow.models.Booking;
import gr.athtech.groupName.rentonow.models.Property;
import gr.athtech.groupName.rentonow.repositories.AvailabilityRepository;
import gr.athtech.groupName.rentonow.services.AvailabilityService;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.http.HttpStatus;

import gr.athtech.groupName.rentonow.dtos.ClosedDatesDto;
import gr.athtech.groupName.rentonow.dtos.PropertyDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Availability;
import gr.athtech.groupName.rentonow.services.PropertyService;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

  @Autowired
  private AvailabilityRepository availabilityRepository;

  @Autowired
  PropertyService propertyService;

  public Availability setBooked(Booking booking, Property property) {
    Availability availability = new Availability();
    availability.setBooking(booking);
    availability.setStartDate(booking.getStartDate());
    availability.setEndDate(booking.getEndDate());
    availability.setProperty(property);
    availability.setStatus(AvailabilityStatus.BOOKED);
    return availabilityRepository.save(availability);
  }

  @Override
  public PropertyDto setClosed(Long propertyId, ClosedDatesDto closedDatesDto) throws BadRequestException,
      NotFoundException {

    if (closedDatesDto.getStartDate() == null || closedDatesDto.getEndDate() == null) {
      throw new BadRequestException("No start date or end date");
    }

    Property property = propertyService.findOwnedProperty(propertyId);

    boolean isBooked = availabilityRepository.isUnavailableForPeriod(propertyId, closedDatesDto.getStartDate(),
        closedDatesDto.getEndDate());
    if (isBooked) {
      throw new BadRequestException("You cannot set the property as closed during a period it is booked");
    }

    Availability availability = new Availability();

    availability.setStartDate(closedDatesDto.getStartDate());
    availability.setEndDate(closedDatesDto.getEndDate());
    availability.setProperty(property);

    return null;
  }

  @Override
  public Boolean isPropertyAvailable(Long propertyId, LocalDate startDate, LocalDate endDate) {
    return !availabilityRepository.isUnavailableForPeriod(propertyId, startDate, endDate);
  }
}
