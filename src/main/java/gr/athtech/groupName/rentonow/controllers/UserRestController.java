package gr.athtech.groupName.rentonow.controllers;

import org.springframework.web.bind.annotation.RestController;

import gr.athtech.groupName.rentonow.constants.SecurityConstants;
import gr.athtech.groupName.rentonow.dtos.UserDto;
import gr.athtech.groupName.rentonow.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
public class UserRestController {

  @Autowired
  UserService userService;

  @PostMapping(value=SecurityConstants.SIGN_UP_URL)
  public UserDto register(@RequestBody UserDto dto) {
      return userService.register(dto);
  }

  @PostMapping(value = "/auth/update")
  public UserDto update(@RequestBody UserDto dto) {
    return userService.update(dto);
  }

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler(DataIntegrityViolationException.class)
  public String usernameErrorHandler() {
    return "username already exists";
  }
  
}
