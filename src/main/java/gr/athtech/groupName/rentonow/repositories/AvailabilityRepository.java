package gr.athtech.groupName.rentonow.repositories;

import gr.athtech.groupName.rentonow.models.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;

public interface AvailabilityRepository extends JpaRepository<Availability, Long>, QuerydslPredicateExecutor<Availability> {

    @Query("select case when count(a) > 0 then true else false end " +
            "from Availability a where a.property.id = ?1 " +
            "and (a.startDate <= ?2 or a.startDate < ?3) " +
            "and (a.endDate >= ?3 or a.endDate > ?2)")
    Boolean isUnavailableForPeriod(Long propertyId, LocalDate startDate, LocalDate endDate);
}
