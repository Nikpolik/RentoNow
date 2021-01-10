package gr.athtech.groupName.rentonow.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FindBookingDto {
    private Long guestId;
    private Long propertyId;
    private LocalDate fromDate;
    private LocalDate toDate;

    public Boolean isEmpty() {
        return guestId == null && propertyId == null && fromDate == null && toDate == null;
    }
}
