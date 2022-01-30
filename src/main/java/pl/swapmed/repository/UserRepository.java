package pl.swapmed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.swapmed.model.User;
import pl.swapmed.model.Workplace;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
    User findUserByEmail(String email);

    @Query(value = "SELECT * FROM user LEFT JOIN schedule s on user.id = s.user_id WHERE workplace_id =?1", nativeQuery = true)
    List<User> findAllUsersInSchedule(Long workplaceId);

}
