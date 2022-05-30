package  com.kowalczyk.iwill.repository;


import com.kowalczyk.iwill.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
