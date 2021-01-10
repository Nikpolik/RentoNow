package gr.athtech.groupName.rentonow.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class StatisticsDto {
  Long total;
  Long id;
  String identifier;
}
