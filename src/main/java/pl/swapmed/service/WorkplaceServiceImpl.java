package pl.swapmed.service;

import org.springframework.stereotype.Service;
import pl.swapmed.model.Workplace;
import pl.swapmed.repository.WorkplaceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkplaceServiceImpl implements WorkplaceService {

    private final WorkplaceRepository workplaceRepository;

    public WorkplaceServiceImpl(WorkplaceRepository workplaceRepository) {
        this.workplaceRepository = workplaceRepository;

    }

    @Override
    public List<Workplace> findAll() {
        return workplaceRepository.findAll();
    }

    @Override
    public void save(Workplace workplace) {
        workplaceRepository.save(workplace);

    }

    @Override
    public Optional<Workplace> findById(Long id) {
        return workplaceRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        workplaceRepository.deleteById(id);
    }

    @Override
    public List<Workplace> findByUsers_Id(Long userId) {
        return workplaceRepository.findByUsers_Id(userId);
    }

    @Override
    public void deleteUserFromWorkplace(Long userId, Long workplaceId) {
        workplaceRepository.deleteUserFromWorkplace(userId, workplaceId);
    }
}
