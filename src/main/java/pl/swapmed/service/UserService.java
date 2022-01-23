package pl.swapmed.service;

import pl.swapmed.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    User findUserByUsername(String name);

    User findUserByEmail(String email);

    Optional<User> findById(Long id);

    void saveUser(User user);

    void delete(Long id);




}
