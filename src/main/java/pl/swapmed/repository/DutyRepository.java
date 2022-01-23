package pl.swapmed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.swapmed.model.Duty;

@Repository
public interface DutyRepository extends JpaRepository<Duty, Long> {
}
