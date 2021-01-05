package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.models.Availability;
import gr.athtech.groupName.rentonow.models.AvailabilityStatus;
import gr.athtech.groupName.rentonow.models.Booking;
import gr.athtech.groupName.rentonow.models.Property;
import gr.athtech.groupName.rentonow.repositories.AvailabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AvailabilityService {

    @Autowired
    private AvailabilityRepository availabilityRepository;

    public Availability createAvailability(Booking booking, Property property) {
        Availability availability = new Availability();
        availability.setBooking(booking);
        availability.setStartDate(booking.getStartDate());
        availability.setEndDate(booking.getEndDate());
        availability.setProperty(property);
        availability.setStatus(AvailabilityStatus.BOOKED);
        return availabilityRepository.save(availability);
    }

    public Boolean isPropertyAvailable(Long propertyId, LocalDate startDate, LocalDate endDate) {
        Integer numberOfOverlappedBookings = availabilityRepository.findOverlappedBookings(propertyId,startDate,endDate);
        return numberOfOverlappedBookings == 0;
    }
}
