package pl.swapmed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.swapmed.model.User;
import pl.swapmed.model.Workplace;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace, Long> {

    List<Workplace> findByUsers_Id(Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE workplace SET city = ?1, name=?2, address=?3, division=?4 WHERE id=?5", nativeQuery = true)
    void updateWorkplace(String city, String name, String address, String division, Long id);

    @Modifying
    @Transactional
    @Query(value = "delete FROM workplace_user where user_id=?1 AND workplace_id=?2", nativeQuery = true)
    void deleteUserFromWorkplace(Long userId, Long workplaceId);

}
