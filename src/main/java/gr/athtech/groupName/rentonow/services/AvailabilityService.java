package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.dtos.AvailabilityDto;
import gr.athtech.groupName.rentonow.dtos.ClosedOrBookedDatesDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {
   Boolean isPropertyAvailable(Long propertyId, LocalDate startDate, LocalDate endDate);

   AvailabilityDto setClosed(Long propertyId, ClosedOrBookedDatesDto closedOrBookedDatesDto) throws BadRequestException, NotFoundException;

   List<AvailabilityDto> getClosedPropertiesDates();

   Boolean deleteAvailability(Long id) throws NotFoundException;
}
