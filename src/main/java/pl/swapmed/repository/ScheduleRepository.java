package pl.swapmed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.swapmed.model.Schedule;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    List<Schedule> findByWorkplaceId(Long workplaceId);
    Optional<Schedule> findScheduleByWorkplace_Id(Long workplaceId);



}
