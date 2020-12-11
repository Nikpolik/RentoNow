package gr.athtech.groupName.rentonow.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity(name = "availabilities")
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne()
    private Property property;
    @OneToOne(mappedBy = "availability")
    private Booking booking;
    @Enumerated(EnumType.ORDINAL)
    private AvailabilityStatus status;
}
