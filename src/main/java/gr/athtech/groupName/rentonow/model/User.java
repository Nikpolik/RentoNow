package gr.athtech.groupName.rentonow.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name="User")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String telephone;
    private String email;
//    @ManyToMany
//    private List<Role> roles;
    @OneToMany(mappedBy = "host")
    private List<Property> properties;
    @OneToMany(mappedBy = "guest")
    private List<Booking> bookings;
}
