package jungle.jungle_week_13.dto;

import jungle.jungle_week_13.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostRespondDto {
    private Long id;
    private String title;
    private User author;
    private String content;
    private LocalDateTime createAt;
}
