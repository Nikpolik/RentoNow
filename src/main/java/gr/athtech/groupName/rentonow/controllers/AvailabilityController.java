package gr.athtech.groupName.rentonow.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.athtech.groupName.rentonow.dtos.AvailabilityDto;
import gr.athtech.groupName.rentonow.services.AvailabilityService;

@RestController
@RequestMapping(value = "availabilities")
public class AvailabilityController {

  @Autowired
  AvailabilityService availabilityService;
  /**
   * 
   * @return return all the availabilities that belong to properties of the current user
   */
  @GetMapping("/")
  public List<AvailabilityDto> getAvailabilities() {
    return availabilityService.getClosedPropertiesDates();
  }

  @PostMapping("/{id}")
  public String removeClosedStatus(@PathVariable Long id) {
    return "success";
  }
}
