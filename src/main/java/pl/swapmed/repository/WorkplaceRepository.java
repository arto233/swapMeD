package pl.swapmed.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.swapmed.model.Workplace;

@Repository
public interface WorkplaceRepository extends JpaRepository<Workplace,Long> {


}