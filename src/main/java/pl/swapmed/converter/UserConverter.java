package pl.swapmed.converter;

import org.springframework.stereotype.Component;
import pl.swapmed.dto.UserDto;
import pl.swapmed.model.User;

@Component
public class UserConverter {

    public UserDto entityToDto(User user) {

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setName(user.getName());
        userDto.setLastName(user.getLastName());
        userDto.setActive(user.getActive());
        userDto.setRoles(user.getRoles());

        return userDto;
    }
    public User dtoToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setActive(userDto.getActive());
        user.setRoles(userDto.getRoles());

        return user;
    }
}
