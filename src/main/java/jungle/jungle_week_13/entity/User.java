package jungle.jungle_week_13.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userId;

    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "commenter")
    private List<Comment> comments = new ArrayList<>();

    public User(String userId, String password, UserRoleEnum role) {
        this.userId = userId;
        this.password = password;
        this.role = role;
    }

}
