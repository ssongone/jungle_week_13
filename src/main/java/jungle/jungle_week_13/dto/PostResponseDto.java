package jungle.jungle_week_13.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jungle.jungle_week_13.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PostResponseDto {
    private String title;
    private String authorUserId;
    private String content;

    @OneToMany(mappedBy = "post")
    private List<CommentResponseDto> comments = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    public PostResponseDto(String title, String authorUserId, String content, LocalDateTime createAt, List<Comment> comments) {
        this.title = title;
        this.authorUserId = authorUserId;
        this.content = content;
        this.comments = makeCommentResponseDto(comments);
        this.createAt = createAt;
    }

    public List<CommentResponseDto> makeCommentResponseDto(List<Comment> comments) {
        return comments.stream()
                .map(comment -> comment.convertToDto())
                .collect(Collectors.toList());
    }
}
