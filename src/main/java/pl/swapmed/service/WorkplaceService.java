package pl.swapmed.service;

import org.hibernate.jdbc.Work;
import pl.swapmed.model.Workplace;

import java.util.List;
import java.util.Optional;

public interface WorkplaceService {

    List<Workplace> findAll();

    void save(Workplace workplace);

    Optional<Workplace> findById(Long id);

    void delete(Long id);

    void updateWorkplace(String city, String name, String address, String division, Long id);

    List<Workplace> findByUsers_Id(Long userId);

    void deleteUserFromWorkplace(Long userId, Long workplaceId);




}
