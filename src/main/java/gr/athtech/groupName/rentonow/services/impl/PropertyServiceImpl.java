package gr.athtech.groupName.rentonow.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import gr.athtech.groupName.rentonow.dtos.CreatePropertyDto;
import gr.athtech.groupName.rentonow.dtos.FindPropertyDto;
import gr.athtech.groupName.rentonow.dtos.PropertyDto;
import gr.athtech.groupName.rentonow.repositories.PropertyRepository;
import gr.athtech.groupName.rentonow.services.PropertyService;
import gr.athtech.groupName.rentonow.models.Property;
import gr.athtech.groupName.rentonow.models.User;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;


    @Override
    public CreatePropertyDto saveProperty(CreatePropertyDto createDto) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Property property = CreatePropertyDto.toProperty(createDto);
        property.setHost(currentUser);
        propertyRepository.save(property);
        return createDto;
    }

    @Override
    public List<PropertyDto> findProperties(FindPropertyDto searchParams) {
        
        List<Property> properties = propertyRepository.findProperties(searchParams);
        List<PropertyDto> dtos = new ArrayList<>();
        properties.forEach(property -> dtos.add(PropertyDto.fromProperty(property)));
        return dtos;
    }
}
