package gr.athtech.groupName.rentonow.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import gr.athtech.groupName.rentonow.dtos.ClosedDatesDto;
import gr.athtech.groupName.rentonow.dtos.CreatePropertyDto;
import gr.athtech.groupName.rentonow.dtos.FindPropertyDto;
import gr.athtech.groupName.rentonow.dtos.PropertyDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.repositories.PropertyRepository;
import gr.athtech.groupName.rentonow.services.PropertyService;
import gr.athtech.groupName.rentonow.models.Property;
import gr.athtech.groupName.rentonow.models.User;

@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    @Override
    public PropertyDto saveProperty(CreatePropertyDto createDto) {
        User currentUser = (User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Property property = CreatePropertyDto.toProperty(createDto);
        property.setHost(currentUser);
        return PropertyDto.fromProperty(propertyRepository.save(property));
    }

    @Override
    public List<PropertyDto> findProperties(FindPropertyDto searchParams) {

        List<Property> properties = propertyRepository.findProperties(searchParams);
        List<PropertyDto> dtos = new ArrayList<>();
        properties.forEach(property -> dtos.add(PropertyDto.fromProperty(property)));
        return dtos;
    }

    @Override
    @PostAuthorize("returnObject.host.id == authentication.principal.id")
    public Property findOwnedProperty(Long id) throws NotFoundException {
        var property = propertyRepository.findById(id);
        if(property.isEmpty()) {
            throw new NotFoundException("No property found");
        }
        return property.get();
    }

    @Override
    public PropertyDto updateProperty(CreatePropertyDto propertyDto) throws NotFoundException {
        Property property = this.findOwnedProperty(propertyDto.getId());

        if (propertyDto.getAddress() != null) {
            property.setAddress(propertyDto.getAddress());
        }
        if (propertyDto.getDetails() != null) {
            property.setDetails(propertyDto.getDetails());
        }
        if (propertyDto.getEmail() != null) {
            property.setEmail(propertyDto.getEmail());
        }
        if (property.getLocation() != null) {
            property.setLocation(propertyDto.getLocation());
        }
        if (property.getTelephone() != null) {
            property.setTelephone(propertyDto.getTelephone());
        }
        if (property.getPrice() != null) {
            property.setPrice(propertyDto.getPrice());
        }

        return PropertyDto.fromProperty(propertyRepository.save(property));
    }

    public Property findPropertyById(Long propertyId) throws NotFoundException {
        return propertyRepository.findById(propertyId).orElseThrow(() -> new NotFoundException("There is no property with the id provided"));
    }
}
