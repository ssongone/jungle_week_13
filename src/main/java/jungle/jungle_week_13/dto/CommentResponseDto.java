package jungle.jungle_week_13.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private String commenter;
    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    public CommentResponseDto(String commenter, String content, LocalDateTime createAt) {
        this.commenter = commenter;
        this.content = content;
        this.createAt = createAt;
    }
}
