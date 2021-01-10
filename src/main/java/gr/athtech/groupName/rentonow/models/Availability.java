package gr.athtech.groupName.rentonow.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@ToString
@JsonIgnoreProperties({"property", "booking"})
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    private Property property;
    @OneToOne
    private Booking booking;
    @Enumerated(EnumType.ORDINAL)
    private AvailabilityStatus status;
}
