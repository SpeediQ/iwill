package  com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
