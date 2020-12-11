package gr.athtech.groupName.rentonow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gr.athtech.groupName.rentonow.models.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
