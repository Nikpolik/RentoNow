package gr.athtech.groupName.rentonow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gr.athtech.groupName.rentonow.model.Property;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
}
