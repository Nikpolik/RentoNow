package gr.athtech.groupName.rentonow.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.athtech.groupName.rentonow.dtos.StatisticsDto;
import gr.athtech.groupName.rentonow.services.StatisticsService;

@RequestMapping(value = "statistics/")
@RestController
public class StatisticsController {

  @Autowired
  StatisticsService statisticsService;

  @GetMapping(value = "/totals/host")
  public List<StatisticsDto> getBookingsPerHost() {
    return statisticsService.bookingsPerHost();
  }

  @GetMapping(value = "/totals/property")
  public List<StatisticsDto> getBookingsPerProperty() {
    return statisticsService.bookingsPerProperty();
  }

  @GetMapping(value = "/totals/guest")
  public List<StatisticsDto> getBookingsPerGuest() {
    return statisticsService.bookingsPerGuest();
  }

}
