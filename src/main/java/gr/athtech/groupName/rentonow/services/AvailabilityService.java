package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.models.Availability;
import gr.athtech.groupName.rentonow.models.Booking;
import gr.athtech.groupName.rentonow.models.Property;

import java.time.LocalDate;
import java.util.List;

import gr.athtech.groupName.rentonow.dtos.AvailabilityDto;
import gr.athtech.groupName.rentonow.dtos.ClosedDatesDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;

public interface AvailabilityService {
   Availability setBooked(Booking booking, Property property);

   Boolean isPropertyAvailable(Long propertyId, LocalDate startDate, LocalDate endDate);

   AvailabilityDto setClosed(Long propertyId, ClosedDatesDto closedDatesDto) throws BadRequestException, NotFoundException;

   List<AvailabilityDto> getClosedPropertiesDates();

   Boolean deleteAvailability(Long id) throws NotFoundException;
}
