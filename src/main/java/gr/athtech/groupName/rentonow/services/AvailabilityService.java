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

   /**
    * Checks if the property with the propertyId provided is
    * available in the provided dates.
    *
    * @param propertyId for the id of the property which
    * this booking concerns
    * @param startDate to indicate the start of the required period
    * @param endDate to indicate the end of the required period
    * @return true if the property is available and false if
    * it is not
    * @throws BadRequestException in case there is no property with the provided propertyId
    */
   Boolean isPropertyAvailable(Long propertyId, LocalDate startDate, LocalDate endDate) throws BadRequestException;

   /**
    * Creates an Availability with status Booked for the
    * provided booking and the provided property.
    *
    * @param booking for the booking for which the availability
    * is created
    * @param property for the property for which the booking
    * @param closedOrBookedDatesDto containing the start and end
    * dates of the availability to be created
    * @return the Availability created
    * @throws BadRequestException in case one of the params is null or the start date is
    * equal or later than the end date or one of the dates is null
    */
   Availability setBooked(Booking booking, Property property, ClosedOrBookedDatesDto closedOrBookedDatesDto) throws BadRequestException;

   /**
    * Creates an Availability with status Closed for the
    * property with the provided propertyId.
    *
    * @param propertyId for the id of the corresponding property
    * @param closedOrBookedDatesDto containing the start and end
    * dates of the availability to be created
    * @return the AvailabilityDto of the Availability created
    * @throws BadRequestException in case one of the params is null or the start date is
    * equal or later than the end date or one of the dates is null
    * @throws NotFoundException in case there is no property with the provided propertyId
    */
   AvailabilityDto setClosed(Long propertyId, ClosedOrBookedDatesDto closedOrBookedDatesDto) throws BadRequestException, NotFoundException;

   /**
    * Finds all of the Availabilities with status Closed
    * where the current user is the host.
    *
    * @return a List of the AvailabilityDtos with status Closed, where
    * the host is the current user
    */
   List<AvailabilityDto> getClosedPropertiesDates();

   /**
    * Deletes the Availability with the provided id.
    *
    * @param id for the id of the Availability to delete
    * @return true if the deletion is successful and false if it is not
    * @throws NotFoundException in case there is no Availability with the provided id
    */
   Boolean deleteAvailability(Long id) throws NotFoundException;
}
