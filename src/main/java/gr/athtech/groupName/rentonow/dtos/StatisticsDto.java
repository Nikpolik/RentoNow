package gr.athtech.groupName.rentonow.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticsDto {
  Long total;
  Long id;
  String identifier;
}
