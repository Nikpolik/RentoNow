package gr.athtech.groupName.rentonow.dtos;

import java.time.LocalDate;

import gr.athtech.groupName.rentonow.models.Availability;
import gr.athtech.groupName.rentonow.models.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@ToString
public class AvailabilityDto {
  LocalDate startDate;
  LocalDate endDate;
  AvailabilityStatus status;

  public static AvailabilityDto fromAvailability(Availability availability) {
    return AvailabilityDto.builder()
                          .startDate(availability.getStartDate())
                          .endDate(availability.getEndDate())
                          .status(availability.getStatus())
                          .build();

  }
}
