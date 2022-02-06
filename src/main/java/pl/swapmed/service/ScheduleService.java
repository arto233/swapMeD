package pl.swapmed.service;

import pl.swapmed.model.Duty;
import pl.swapmed.model.Schedule;
import pl.swapmed.model.User;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {

    List<Schedule> findAll();

    void save(Schedule schedule);

    Optional<Schedule> findById(Long id);

    void delete(Long id);

    List<Schedule> findByWorkplaceId(Long workplaceId);

    List<Schedule> findByWorkplace_IdAndMonthAndYear(Long workplaceId, Integer month, Integer year);

    List<Schedule> findByWorkplace_IdAndUser(Long workplaceId, User user);

    Optional<Schedule> findScheduleByWorkplaceId(Long workplaceId);

    void deleteUserFromSchedule(Long userId, Long workplaceId);


}
