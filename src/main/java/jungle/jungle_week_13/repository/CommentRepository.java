package jungle.jungle_week_13.repository;

import jungle.jungle_week_13.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
