package gr.athtech.groupName.rentonow.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String telephone;
    private String email;
    private String role;
    @OneToMany(mappedBy = "host")
    private List<Property> properties;
    @OneToMany(mappedBy = "guest")
    private List<Booking> bookings;
}
