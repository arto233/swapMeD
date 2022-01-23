package pl.swapmed.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import pl.swapmed.model.Role;

import java.util.Set;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    String username;
    String email;
    String password;
    String name;
    String lastName;
    Boolean active;
    Set<Role> roles;

}
