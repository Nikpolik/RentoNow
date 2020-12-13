package gr.athtech.groupName.rentonow.service;

import gr.athtech.groupName.rentonow.model.Property;

public interface PropertyService {
    Property getById(long id);
    List<Property> getAllProperties();

}
