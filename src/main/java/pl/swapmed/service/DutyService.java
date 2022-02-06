package pl.swapmed.service;

import pl.swapmed.model.Duty;
import pl.swapmed.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DutyService {

    List<Duty> findAll();

    List<Duty> findAllByScheduleId(Long scheduleId);

    void save(Duty duty);

    Optional<Duty> findById(Long id);

    void delete(Long id);

    void deleteDutiesByUserId(Long userId);

    List<Duty> findAllBySchedule_MonthAndSchedule_YearAndSchedule_Workplace_Id(Integer month, Integer year, Long workplaceId);

    List<Duty> findAllBySchedule_MonthAndSchedule_YearAndUser(Integer month, Integer year, User user);

    List<Duty> findAllByUserAndStartNotAndEndNot(User user, LocalDateTime start, LocalDateTime end);

}
