package pl.swapmed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.swapmed.model.Schedule;
import pl.swapmed.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByWorkplaceId(Long workplaceId);

    List<Schedule> findByWorkplace_IdAndUser(Long workplaceId, User user);

    List<Schedule> findByWorkplace_IdAndMonthAndYear(Long workplaceId, Integer month, Integer year);

    Optional<Schedule> findScheduleByWorkplace_Id(Long workplaceId);



}
