package jungle.jungle_week_13.entity;

import jungle.jungle_week_13.dto.PostRequestDto;
import jungle.jungle_week_13.dto.PostRespondDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Column(nullable = false)
    private String author;

    @Column
    private String content;

    @Column(nullable = false)
    private String password;

    public Post(String title, String author, String content, String password) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.password = password;
    }
    public Post(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.author = postRequestDto.getAuthor();
        this.content = postRequestDto.getContent();
        this.password = postRequestDto.getPassword();
    }

    public PostRespondDto convertToDto() {
        PostRespondDto dto = new PostRespondDto();
        dto.setTitle(this.title);
        dto.setAuthor(this.author);
        dto.setContent(this.content);
        dto.setCreateAt(this.getCreateAt());

        return dto;
    }
}
