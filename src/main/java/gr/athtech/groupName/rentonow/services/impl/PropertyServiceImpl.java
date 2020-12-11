package gr.athtech.groupName.rentonow.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.athtech.groupName.rentonow.dtos.CreatePropertyDto;
import gr.athtech.groupName.rentonow.repositories.PropertyRepository;
import gr.athtech.groupName.rentonow.services.PropertyService;
import gr.athtech.groupName.rentonow.models.Property;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public CreatePropertyDto saveProperty(CreatePropertyDto propertyDto) {
        Property property = CreatePropertyDto.toProperty(propertyDto);
        propertyRepository.save(property);
        return propertyDto;
    }
    
}
