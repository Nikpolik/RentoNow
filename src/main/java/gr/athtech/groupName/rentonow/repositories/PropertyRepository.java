package gr.athtech.groupName.rentonow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.athtech.groupName.rentonow.models.Property;

public interface PropertyRepository extends JpaRepository<Property, Long>, PropertyRepositoryCustom {
}
