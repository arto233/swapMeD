package pl.swapmed.service;

import org.springframework.stereotype.Service;
import pl.swapmed.model.Duty;
import pl.swapmed.model.User;
import pl.swapmed.repository.DutyRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DutyServiceImpl implements DutyService {

    private final DutyRepository dutyRepository;

    public DutyServiceImpl(DutyRepository dutyRepository) {
        this.dutyRepository = dutyRepository;
    }

    @Override
    public List<Duty> findAll() {
        return dutyRepository.findAll();
    }

    @Override
    public List<Duty> findAllByScheduleId(Long scheduleId) {
        return dutyRepository.findAllBySchedule_Id(scheduleId);
    }

    @Override
    public void save(Duty duty) {
        dutyRepository.save(duty);
    }

    @Override
    public Optional<Duty> findById(Long id) {
        return dutyRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        dutyRepository.deleteById(id);
    }

    @Override
    public void deleteDutiesByUserId(Long userId) {
        dutyRepository.deleteDutiesByUserId(userId);
    }

    @Override
    public List<Duty> findAllBySchedule_MonthAndSchedule_YearAndSchedule_Workplace_Id(Integer month, Integer year, Long workplaceId) {
        return dutyRepository.findAllBySchedule_MonthAndSchedule_YearAndSchedule_Workplace_Id(month, year, workplaceId);
    }

    @Override
    public List<Duty> findAllBySchedule_MonthAndSchedule_YearAndUser(Integer month, Integer year, User user) {
        return dutyRepository.findAllBySchedule_MonthAndSchedule_YearAndUser(month, year, user);
    }

    @Override
    public List<Duty> findAllByUserAndStartNotAndEndNot(User user, LocalDateTime start, LocalDateTime end) {
        return dutyRepository.findAllByUserAndStartNotAndEndNot(user, start, end);
    }
}
