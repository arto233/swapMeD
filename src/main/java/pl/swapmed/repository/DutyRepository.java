package pl.swapmed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.swapmed.model.Duty;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DutyRepository extends JpaRepository<Duty, Long> {

    List<Duty> findAllBySchedule_Id(Long scheduleId);

    @Modifying
    @Transactional
    @Query(value = "delete FROM duties where user_id=?1", nativeQuery = true)
    void deleteDutiesByUserId(Long userId);
}
