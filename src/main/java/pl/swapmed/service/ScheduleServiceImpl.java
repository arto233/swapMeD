package pl.swapmed.service;

import org.springframework.stereotype.Service;
import pl.swapmed.model.Schedule;
import pl.swapmed.model.User;
import pl.swapmed.repository.ScheduleRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Override
    public void save(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    @Override
    public Optional<Schedule> findById(Long id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }

    @Override
    public List<Schedule> findByWorkplaceId(Long workplaceId) {
        return scheduleRepository.findByWorkplaceId(workplaceId);
    }

    @Override
    public List<Schedule> findByWorkplace_IdAndMonthAndYear(Long workplaceId, Integer month, Integer year) {
        return scheduleRepository.findByWorkplace_IdAndMonthAndYear(workplaceId, month, year);
    }

    @Override
    public List<Schedule> findByWorkplace_IdAndUser(Long workplaceId, User user) {
        return scheduleRepository.findByWorkplace_IdAndUser(workplaceId, user);
    }

    @Override
    public Optional<Schedule> findScheduleByWorkplaceId(Long workplaceId) {
        return scheduleRepository.findScheduleByWorkplace_Id(workplaceId);
    }

    @Override
    public void deleteUserFromSchedule(Long userId, Long workplaceId) {
        scheduleRepository.deleteUserFromSchedule(userId, workplaceId);
    }
}
