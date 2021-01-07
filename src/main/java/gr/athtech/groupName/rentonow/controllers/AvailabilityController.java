package gr.athtech.groupName.rentonow.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.athtech.groupName.rentonow.dtos.AvailabilityDto;

@RestController
@RequestMapping(value = "availabilities")
public class AvailabilityController {

  /**
   * 
   * @return return all the availabilities that bellong to properties of the current user
   */
  @GetMapping("/")
  public AvailabilityDto getAvailabilities() {
    return null;
  }

}
