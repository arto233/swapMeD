package pl.swapmed.service;

import pl.swapmed.model.User;

public interface UserService {

    User findByUserName(String name);

    void saveUser(User user);
}
