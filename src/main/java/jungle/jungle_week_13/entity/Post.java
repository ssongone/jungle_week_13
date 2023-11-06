package jungle.jungle_week_13.entity;

import jungle.jungle_week_13.dto.PostRequestDto;
import jungle.jungle_week_13.dto.PostRespondDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// - 제목, 작성자명, 작성 내용, 작성 날짜를 조회하기
@Getter
@Entity
@NoArgsConstructor
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

    public Post(String title, User author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }
    public Post(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }

    public PostRespondDto convertToDto() {
        PostRespondDto dto = new PostRespondDto();
        dto.setId(this.id);
        dto.setTitle(this.title);
        dto.setAuthor(this.author);
        dto.setContent(this.content);
        dto.setCreateAt(this.getCreateAt());

        return dto;
    }

    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.content = postRequestDto.getContent();
    }
}
