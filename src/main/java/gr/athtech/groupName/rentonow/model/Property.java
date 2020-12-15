package gr.athtech.groupName.rentonow.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private String location;
    private String details;
    private String telephone;
    private String email;
    @ManyToOne
    private User host;
    @OneToMany(mappedBy = "property")
    private List<Payment> payments;
    @OneToMany(mappedBy = "property")
    private List<Booking> bookings;
}
