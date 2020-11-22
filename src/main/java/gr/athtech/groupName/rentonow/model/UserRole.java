package gr.athtech.groupName.rentonow.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "UserRole")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User user;
    private Role role;
}
