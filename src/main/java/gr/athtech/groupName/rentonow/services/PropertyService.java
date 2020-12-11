package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.dtos.CreatePropertyDto;

public interface PropertyService {
   CreatePropertyDto saveProperty(CreatePropertyDto propertyDto); 
}
