package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.dtos.UserDto;

public interface UserService {

  UserDto register(UserDto newUserDto);

  UserDto update(UserDto updateUser);

  Boolean makeUserAdmin(Long id);

}
