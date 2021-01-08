package gr.athtech.groupName.rentonow.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties("location")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;
    private Point location;
    private String address;
    private String details;
    private String telephone;
    private String email;

    @ManyToOne
    private User host;

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Booking> bookings;
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Availability> availabilities;
}
