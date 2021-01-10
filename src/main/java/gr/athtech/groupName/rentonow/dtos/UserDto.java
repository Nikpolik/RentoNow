package gr.athtech.groupName.rentonow.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import gr.athtech.groupName.rentonow.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
@ToString
public class UserDto {
  private String username;
  private String password;
  private String name;
  private String telephone;
  private String email;

  public static User toUser(UserDto dto) {
    User user = new User();
    user.setUsername(dto.getUsername());
    user.setName(dto.getName());
    user.setTelephone(dto.getTelephone());
    user.setEmail(dto.getEmail());
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
