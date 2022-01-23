package pl.swapmed.service;

import pl.swapmed.model.Workplace;

import java.util.List;
import java.util.Optional;

public interface WorkplaceService {

    List<Workplace> findAll();

    void save(Workplace workplace);

    Optional<Workplace> findById(Long id);

    void delete(Long id);



}
