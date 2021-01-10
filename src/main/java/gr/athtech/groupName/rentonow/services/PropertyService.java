package gr.athtech.groupName.rentonow.services;

import java.util.List;

import gr.athtech.groupName.rentonow.dtos.FindPropertyDto;
import gr.athtech.groupName.rentonow.dtos.CreatePropertyDto;
import gr.athtech.groupName.rentonow.dtos.PropertyDto;
import gr.athtech.groupName.rentonow.dtos.UpdatePropertyDto;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Property;

public interface PropertyService {

   /**
    * Creates and saves a property with the details provided
    * in the propertyDto.
    *
    * @param propertyDto containing the required details for the
    * property to be created
    * @return the PropertyDto of the Property created
    */
   PropertyDto saveProperty(CreatePropertyDto propertyDto);

   /**
    * Updates a property with the details provided
    * in the propertyDto.
    *
    * @param propertyDto containing the required details for the
    * property to be updated
    * @return the PropertyDto of the Property updated
    * @throws NotFoundException in case the property
    * to be updated does not exist
    */
   PropertyDto updateProperty(UpdatePropertyDto propertyDto) throws NotFoundException;

   /**
    * Returns a property only if it is owned by the current
    * user or the current user is administrator.
    *
    * @param id for the id of the property
    * @return the Property with the provided id if it is owned by the current
    * user or the current user is administrator.
    * @throws NotFoundException in case there is no property with the provided id
    */
   Property findOwnedProperty(Long id) throws NotFoundException;

   /**
    * Retrieves the properties matching the searchParams.
    *
    * @param searchParams containing the params used for the search
    * @return the List of the required Properties
    */
   List<PropertyDto> findProperties(FindPropertyDto searchParams);

   /**
    * Retrieves the property with the provided id.
    *
    * @param id for the id of the property required
    * @return the Property required
    * @throws NotFoundException in case there is no property with the provided id
    */
   Property findPropertyById(Long id) throws NotFoundException;
}
