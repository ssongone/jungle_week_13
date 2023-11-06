package jungle.jungle_week_13.entity;

import jungle.jungle_week_13.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User commenter;

    private String content;

    public Comment(Post post, User user, String content) {
        this.post = post;
        this.commenter = user;
        this.content = content;
    }

    public void update(CommentRequestDto dto) {
        this.content = dto.getContent();
    }



}
