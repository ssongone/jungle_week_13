package jungle.jungle_week_13.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jungle.jungle_week_13.dto.PostRequestDto;
import jungle.jungle_week_13.dto.PostResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
@Getter
@Entity
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Post extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @Column
    private String content;

    @OrderBy("create_at desc")
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, User author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }
    public Post(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

    public PostResponseDto convertToDto() {
        PostResponseDto dto = new PostResponseDto(
                this.getTitle(),
                this.getAuthor().getUserId(),
                this.getContent(),
                this.getCreateAt(),
                this.getComments()
        );
        return dto;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
