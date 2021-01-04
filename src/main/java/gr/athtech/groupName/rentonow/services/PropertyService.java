package gr.athtech.groupName.rentonow.services;

import java.util.List;

import gr.athtech.groupName.rentonow.dtos.CreatePropertyDto;
import gr.athtech.groupName.rentonow.dtos.FindPropertyDto;
import gr.athtech.groupName.rentonow.dtos.PropertyDto;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;

public interface PropertyService {
   PropertyDto saveProperty(CreatePropertyDto propertyDto);
   PropertyDto updateProperty(CreatePropertyDto propertyDto) throws NotFoundException;
   public List<PropertyDto> findProperties(FindPropertyDto searchParams);
}
