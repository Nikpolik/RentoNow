package gr.athtech.groupName.rentonow.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gr.athtech.groupName.rentonow.dtos.UserDto;
import gr.athtech.groupName.rentonow.models.Role;
import gr.athtech.groupName.rentonow.models.User;
import gr.athtech.groupName.rentonow.repositories.UserRepository;
import gr.athtech.groupName.rentonow.services.UserService;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDto register(UserDto newUserDto) {
    User user = UserDto.toUser(newUserDto);
    String hashedPassword = passwordEncoder.encode(newUserDto.getPassword());
    user.setPassword(hashedPassword);
    user.setRole(Role.USER);
    user = userRepository.save(user);
    return UserDto.fromUser(user);
  }


  @Override
  public UserDto update(UserDto updateUser) {
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    if (updateUser.getTelephone() != null) {
      currentUser.setTelephone(updateUser.getTelephone());
    }

    if(updateUser.getEmail() != null) {
      currentUser.setEmail(updateUser.getEmail());
    }

    if(updateUser.getName() != null) {
      currentUser.setEmail(updateUser.getEmail());
    }

    if(updateUser.getPassword() != null) {
      String hashedPassword = passwordEncoder.encode(updateUser.getPassword());
      currentUser.setPassword(hashedPassword);
    }

    userRepository.save(currentUser);

    return UserDto.fromUser(currentUser);
  }

  @Override
  @Secured("ADMIN")
  public Boolean makeUserAdmin(Long id) {
    User user = userRepository.getOne(id);
    user.setRole(Role.ADMIN);
    userRepository.save(user);
    return true;
  }
}
