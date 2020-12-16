package gr.athtech.groupName.rentonow.repositories;

import java.util.List;

import gr.athtech.groupName.rentonow.dtos.FindPropertyDto;
import gr.athtech.groupName.rentonow.models.Property;

public interface PropertyRepositoryCustom {
    List<Property> findProperties(FindPropertyDto searchParams);
}
