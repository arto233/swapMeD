package pl.swapmed.service;

import pl.swapmed.model.Duty;
import pl.swapmed.model.User;
import pl.swapmed.model.Workplace;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    List<User> findAllUsersInSchedule(Long workplaceId);

    List<User> findAllUsersToShift(Long workplaceId, Integer month, Integer year);

    User findUserByUsername(String name);

    User findUserByEmail(String email);

    Optional<User> findById(Long id);


    void saveUser(User user);

    void delete(Long id);





}
