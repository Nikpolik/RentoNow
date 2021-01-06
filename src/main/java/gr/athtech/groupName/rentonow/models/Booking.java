package gr.athtech.groupName.rentonow.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@JsonIgnoreProperties({"property", "guest", "availability"})
public class Booking {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    private User guest;
    @ManyToOne
    private Property property;
    private LocalDateTime creationDate;
    @OneToOne(cascade = CascadeType.ALL)
    private Availability availability;
}
