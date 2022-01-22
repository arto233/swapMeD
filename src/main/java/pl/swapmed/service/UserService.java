package pl.swapmed.service;

import pl.swapmed.model.User;

public interface UserService {

    User findUserByUsername(String name);

    User findUserByEmail(String email);

    void saveUser(User user);
}
