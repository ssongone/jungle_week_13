package jungle.jungle_week_13.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostRespondDto {
    private String title;
    private String author;
    private String content;
    private LocalDateTime createAt;
}
