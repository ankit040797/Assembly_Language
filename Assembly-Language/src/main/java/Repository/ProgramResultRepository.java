package Repository;

import model.ProgramResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramResultRepository extends JpaRepository<ProgramResult, Long> {
}
