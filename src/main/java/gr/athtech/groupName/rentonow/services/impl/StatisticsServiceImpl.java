package gr.athtech.groupName.rentonow.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.querydsl.core.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import gr.athtech.groupName.rentonow.daos.StatisticsDao;
import gr.athtech.groupName.rentonow.dtos.StatisticsDto;
import gr.athtech.groupName.rentonow.services.StatisticsService;

@Service
@PreAuthorize("hasRole(admin)")
public class StatisticsServiceImpl implements StatisticsService {

  @Autowired
  StatisticsDao statisticsDao;

  @Override
  public List<StatisticsDto> bookingsPerHost() {
    List<Tuple> results = statisticsDao.bookingsPerHost();
    return generateDtoList(results);
  }

  @Override
  public List<StatisticsDto> bookingsPerGuest() {
    List<Tuple> results = statisticsDao.bookingsPerGuest();
    return generateDtoList(results);
  }

  @Override
  public List<StatisticsDto> bookingsPerProperty() {
    List<Tuple> results = statisticsDao.bookingsPerProperty();
    return generateDtoList(results);
  }

  private List<StatisticsDto> generateDtoList(List<Tuple> tuples) {
    return tuples
              .stream()
              .map(this::generateDto)
              .collect(Collectors.toList());
  }

  // we should propably keep this in a intermediate class
  // and not transform it directly to dto
  // since this is a simple problem we should just ignore
  private StatisticsDto generateDto(Tuple tuple) {
    Long id = tuple.get(0, Long.class);
    Long count = tuple.get(1, Long.class);
    String identifier = tuple.get(2, String.class);
    return StatisticsDto
              .builder()
              .total(count)
              .id(id)
              .identifier(identifier)
              .build();
  }

}
