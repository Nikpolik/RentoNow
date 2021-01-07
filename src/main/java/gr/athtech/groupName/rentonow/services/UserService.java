package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.dtos.UserDto;

public interface UserService {

  public UserDto register(UserDto newUserDto);

  public UserDto update(UserDto updateUser);

  public Boolean makeUserAdmin(Long id);

}
