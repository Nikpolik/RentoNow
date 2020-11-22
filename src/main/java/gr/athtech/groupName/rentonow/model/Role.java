package gr.athtech.groupName.rentonow.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
//    @ManyToMany(mappedBy = "roles")
//    private List<Right> rights;
//    @ManyToMany(mappedBy = "roles")
//    private List<User> users;
}
