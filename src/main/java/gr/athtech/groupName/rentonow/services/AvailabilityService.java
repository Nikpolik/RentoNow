package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.dtos.AvailabilityDto;
import gr.athtech.groupName.rentonow.dtos.ClosedOrBookedDatesDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Availability;
import gr.athtech.groupName.rentonow.models.Booking;
import gr.athtech.groupName.rentonow.models.Property;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {
   Boolean isPropertyAvailable(Long propertyId, LocalDate startDate, LocalDate endDate) throws BadRequestException;

   public Availability setBooked(Booking booking, Property property, ClosedOrBookedDatesDto closedOrBookedDatesDto) throws BadRequestException;

   AvailabilityDto setClosed(Long propertyId, ClosedOrBookedDatesDto closedOrBookedDatesDto) throws BadRequestException, NotFoundException;

   List<AvailabilityDto> getClosedPropertiesDates();

   Boolean deleteAvailability(Long id) throws NotFoundException;
}
