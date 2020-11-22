package gr.athtech.groupName.rentonow.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Booking {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    @ManyToOne
    private User user; //guest
    @ManyToOne
    private Property property;
}
