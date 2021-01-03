package gr.athtech.groupName.rentonow.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.athtech.groupName.rentonow.models.User;

public interface UserRepository extends JpaRepository<User, Long>  {
   User findByUsername(String username);  
}
