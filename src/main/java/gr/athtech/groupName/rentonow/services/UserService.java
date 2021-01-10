package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.dtos.UserDto;

public interface UserService {

  /**
   * Registers a user with the details provided in
   * the newUserDto.
   *
   * @param newUserDto containing the details of the user
   * to be registered
   * @return the UserDto of the User registered
   */
  UserDto register(UserDto newUserDto);

  /**
   * Updates a user with the details provided in
   * the updateUser.
   *
   * @param updateUser containing the details of the user
   * to be updated
   * @return the UserDto of the User updated
   */
  UserDto update(UserDto updateUser);

  /**
   * Sets the role of a user as Admin.
   *
   * @param id of the user to be set as Admin
   * @return true in case the setting of the
   * user's role as Admin is successful or false
   * in case it fails
   */
  Boolean makeUserAdmin(Long id);

}
