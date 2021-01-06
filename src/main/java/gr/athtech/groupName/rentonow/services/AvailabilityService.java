package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.models.Availability;
import gr.athtech.groupName.rentonow.models.Booking;
import gr.athtech.groupName.rentonow.models.Property;

import java.time.LocalDate;

public interface AvailabilityService {
    Availability createAvailability(Booking booking, Property property);
    Boolean isPropertyAvailable(Long propertyId, LocalDate startDate, LocalDate endDate);
}
