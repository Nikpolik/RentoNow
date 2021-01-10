package gr.athtech.groupName.rentonow.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import gr.athtech.groupName.rentonow.aspect.Logged;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@ToString
@JsonIgnoreProperties({"property", "guest"})
public class Booking {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User guest;
    @ToString.Exclude
    @ManyToOne
    private Property property;
    private BigDecimal price;
    private LocalDateTime creationDate;
    @OneToOne(cascade = CascadeType.ALL)
    private Availability availability;
    @OneToOne(cascade = CascadeType.ALL)
    private Payment payment;
}
