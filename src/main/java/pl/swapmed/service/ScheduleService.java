package pl.swapmed.service;

import pl.swapmed.model.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {

    List<Schedule> findAll();

    void save(Schedule schedule);

    Optional<Schedule> findById(Long id);

    void delete(Long id);


}
