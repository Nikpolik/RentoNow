package gr.athtech.groupName.rentonow.services;

import java.util.List;

import gr.athtech.groupName.rentonow.dtos.CreatePropertyDto;
import gr.athtech.groupName.rentonow.dtos.FindPropertyDto;
import gr.athtech.groupName.rentonow.dtos.PropertyDto;

public interface PropertyService {
   CreatePropertyDto saveProperty(CreatePropertyDto propertyDto);

   public List<PropertyDto> findProperties(FindPropertyDto searchParams);
}
