package gr.athtech.groupName.rentonow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.athtech.groupName.rentonow.dtos.CreatePropertyDto;
import gr.athtech.groupName.rentonow.services.PropertyService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@RestController()
@RequestMapping("/properties")
public class PropertyRestController {

    @Autowired
    PropertyService service;

    @GetMapping("/")
    public String getProperties() {
        return "properties";
    }

    @GetMapping("/{id}")
    public String getProperty() {
        return "getProperty";
    }

    @PostMapping("/")
    public CreatePropertyDto createProperty(@RequestBody CreatePropertyDto propertyDto) {
        return service.saveProperty(propertyDto);
    }
}
