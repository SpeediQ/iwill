package  com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Integer> {
}
