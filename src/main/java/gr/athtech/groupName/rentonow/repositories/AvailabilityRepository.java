package gr.athtech.groupName.rentonow.repositories;

import gr.athtech.groupName.rentonow.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    @Query("select count(a) from Availability a where a.property.id = ?1 and (a.startDate <= ?2 or a.startDate < ?3) and (a.endDate >= ?3 or a.endDate > ?2)")
    Integer findOverlappedBookings(Long propertyId, LocalDate startDate, LocalDate endDate);
}
