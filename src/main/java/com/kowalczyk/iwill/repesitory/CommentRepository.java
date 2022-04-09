package com.kowalczyk.iwill.repesitory;

import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
