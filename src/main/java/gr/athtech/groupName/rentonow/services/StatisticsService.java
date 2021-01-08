package gr.athtech.groupName.rentonow.services;

import java.util.List;

import gr.athtech.groupName.rentonow.dtos.StatisticsDto;

public interface StatisticsService {
  List<StatisticsDto> bookingsPerHost();
  List<StatisticsDto> bookingsPerGuest();
  List<StatisticsDto> bookingsPerProperty();
}
