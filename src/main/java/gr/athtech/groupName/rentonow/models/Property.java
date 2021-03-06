package gr.athtech.groupName.rentonow.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@ToString
@JsonIgnoreProperties("location")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;
    @ToString.Exclude
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
    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL)
    private List<Image> images;
}
