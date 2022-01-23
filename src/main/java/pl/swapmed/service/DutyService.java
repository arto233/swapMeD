package pl.swapmed.service;

import pl.swapmed.model.Duty;

import java.util.List;
import java.util.Optional;

public interface DutyService {

    List<Duty> findAll();

    void save(Duty duty);

    Optional<Duty> findById(Long id);

    void delete(Long id);
}
