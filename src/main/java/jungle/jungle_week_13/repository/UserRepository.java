package jungle.jungle_week_13.repository;

import jungle.jungle_week_13.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(String userId);

}
