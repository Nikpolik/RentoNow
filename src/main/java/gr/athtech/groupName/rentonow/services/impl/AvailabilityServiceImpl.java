package gr.athtech.groupName.rentonow.services.impl;

import gr.athtech.groupName.rentonow.dtos.AvailabilityDto;
import gr.athtech.groupName.rentonow.dtos.ClosedOrBookedDatesDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.*;
import gr.athtech.groupName.rentonow.repositories.AvailabilityRepository;
import gr.athtech.groupName.rentonow.services.AvailabilityService;
import gr.athtech.groupName.rentonow.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

  @Autowired
  private AvailabilityRepository availabilityRepository;

  @Autowired
  PropertyService propertyService;

  public Availability setBooked(Booking booking, Property property, ClosedOrBookedDatesDto closedOrBookedDatesDto) {
    Availability availability = new Availability();
    availability.setBooking(booking);
    availability.setStartDate(closedOrBookedDatesDto.getStartDate());
    availability.setEndDate(closedOrBookedDatesDto.getEndDate());
    availability.setProperty(property);
    availability.setStatus(AvailabilityStatus.BOOKED);
    return availabilityRepository.save(availability);
  }

  @Override
  public AvailabilityDto setClosed(Long propertyId, ClosedOrBookedDatesDto closedOrBookedDatesDto)
      throws BadRequestException, NotFoundException {

    if (closedOrBookedDatesDto.getStartDate() == null || closedOrBookedDatesDto.getEndDate() == null) {
      throw new BadRequestException("No start date or end date");
    }

    LocalDate start = closedOrBookedDatesDto.getStartDate();
    LocalDate end = closedOrBookedDatesDto.getEndDate();
    if (start.isAfter(end)) {
      start = closedOrBookedDatesDto.getEndDate();
      end = closedOrBookedDatesDto.getEndDate();
    }

    Property property = propertyService.findOwnedProperty(propertyId);

    boolean isBooked = availabilityRepository.isUnavailableForPeriod(propertyId, closedOrBookedDatesDto.getStartDate(),
        closedOrBookedDatesDto.getEndDate());
    if (isBooked) {
      throw new BadRequestException("You cannot set the property as closed during a period it is booked");
    }

    Availability availability = new Availability();

    availability.setStartDate(start);
    availability.setEndDate(end);
    availability.setProperty(property);
    availabilityRepository.save(availability);

    return AvailabilityDto.fromAvailability(availability);
  }

  @Override
  public Boolean isPropertyAvailable(Long propertyId, LocalDate startDate, LocalDate endDate) {
    return !availabilityRepository.isUnavailableForPeriod(propertyId, startDate, endDate);
  }

  @Override
  public List<AvailabilityDto> getClosedPropertiesDates() {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    QAvailability qAvailability = QAvailability.availability;
    Iterable<Availability> availabilities = availabilityRepository.findAll(qAvailability.property.host.eq(currentUser));
    List<AvailabilityDto> dtos = new ArrayList<>();
    availabilities.forEach(availability -> dtos.add(AvailabilityDto.fromAvailability(availability)));
    return dtos;
  }

  @Override
  public Boolean deleteAvailability(Long id) throws NotFoundException {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Availability availability = availabilityRepository.getOne(id);
    if (availability.getProperty().getHost().getId().equals(currentUser.getId())) {
      throw new NotFoundException("No availability found with this id");
    }

    availabilityRepository.delete(availability);
    return true;
  }
}
