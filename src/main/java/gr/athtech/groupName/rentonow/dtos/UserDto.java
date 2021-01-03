package gr.athtech.groupName.rentonow.dtos;

import gr.athtech.groupName.rentonow.models.Role;
import gr.athtech.groupName.rentonow.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private String username;
  private String password;
  private String name;
  private String telephone;
  private String email;
  private Role role = Role.USER;

  public static User toUser(UserDto dto) {
    User user = new User();
    user.setUsername(dto.getUsername());
    user.setName(dto.getName());
    user.setTelephone(dto.getTelephone());
    user.setEmail(dto.getEmail());
    user.setRole(dto.getRole());
    return user;
  }

  public static UserDto fromUser(User user) {
    UserDto dto = new UserDto();
    dto.setUsername(user.getUsername());
    dto.setName(user.getName());
    dto.setTelephone(user.getTelephone());
    dto.setEmail(user.getEmail());
    return dto;
  }
}
