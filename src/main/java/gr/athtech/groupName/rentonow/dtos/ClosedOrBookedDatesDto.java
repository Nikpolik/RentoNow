package gr.athtech.groupName.rentonow.dtos;

import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Data
@ToString
public class ClosedOrBookedDatesDto {
  private LocalDate startDate;
  private LocalDate endDate;
}
