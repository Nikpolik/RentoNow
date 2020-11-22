package gr.athtech.groupName.rentonow.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Right {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
//    @ManyToMany
//    private List<Role> roles;

}
