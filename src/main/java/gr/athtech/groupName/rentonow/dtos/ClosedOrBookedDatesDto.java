package gr.athtech.groupName.rentonow.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClosedOrBookedDatesDto {
  private LocalDate startDate;
  private LocalDate endDate;
}
