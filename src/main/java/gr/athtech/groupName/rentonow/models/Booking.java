package gr.athtech.groupName.rentonow.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@JsonIgnoreProperties({"property", "guest"})
public class Booking {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User guest;
    @ManyToOne
    private Property property;
    private LocalDateTime creationDate;
    @OneToOne(cascade = CascadeType.ALL)
    private Availability availability;
    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;
}
