package gr.athtech.groupName.rentonow.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gr.athtech.groupName.rentonow.dtos.UserDto;
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
    userRepository.save(user);
    return UserDto.fromUser(user);
  }
}
