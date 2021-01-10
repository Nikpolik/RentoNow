package gr.athtech.groupName.rentonow.services;

import java.util.List;

import gr.athtech.groupName.rentonow.dtos.StatisticsDto;

public interface StatisticsService {

  /**
   * Retrieves the bookings per host.
   *
   * @return a List of the required StatisticsDto
   */
  List<StatisticsDto> bookingsPerHost();

  /**
   * Retrieves the bookings per guest.
   *
   * @return a List of the required StatisticsDto
   */
  List<StatisticsDto> bookingsPerGuest();

  /**
   * Retrieves the bookings per property.
   *
   * @return a List of the required StatisticsDto
   */
  List<StatisticsDto> bookingsPerProperty();
}
