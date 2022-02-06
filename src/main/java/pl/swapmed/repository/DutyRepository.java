package pl.swapmed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.swapmed.model.Duty;
import pl.swapmed.model.User;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DutyRepository extends JpaRepository<Duty, Long> {

    List<Duty> findAllBySchedule_Id(Long scheduleId);

    List<Duty> findAllBySchedule_MonthAndSchedule_YearAndSchedule_Workplace_Id(Integer month, Integer year, Long workplaceId);

    List<Duty> findAllBySchedule_MonthAndSchedule_YearAndUser(Integer month, Integer year, User user);

    List<Duty> findAllByUserAndStartNotAndEndNot(User user, LocalDateTime start, LocalDateTime end);

    @Modifying
    @Transactional
    @Query(value = "delete FROM duties where user_id=?1", nativeQuery = true)
    void deleteDutiesByUserId(Long userId);
}
