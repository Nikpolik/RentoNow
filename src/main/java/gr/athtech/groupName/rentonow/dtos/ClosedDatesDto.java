package gr.athtech.groupName.rentonow.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ClosedDatesDto {
  private LocalDate startDate;
  private LocalDate endDate;
}
