package jungle.jungle_week_13.repository;

import jungle.jungle_week_13.dto.PostSummary;
import jungle.jungle_week_13.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PostRepository extends JpaRepository<Post, Long> {

    List<PostSummary> findAllByOrderByCreateAtDesc();
    Optional<Post> findById(Long id);

}
