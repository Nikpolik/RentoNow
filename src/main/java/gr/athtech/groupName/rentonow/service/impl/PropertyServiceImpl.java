package gr.athtech.groupName.rentonow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gr.athtech.groupName.rentonow.dto.CreatePropertyDto;
import gr.athtech.groupName.rentonow.repository.PropertyRepository;
import gr.athtech.groupName.rentonow.service.PropertyService;
import gr.athtech.groupName.rentonow.model.Property;

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
