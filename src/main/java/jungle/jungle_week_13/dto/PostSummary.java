package jungle.jungle_week_13.dto;

import java.time.LocalDateTime;

public interface PostSummary {
    String getTitle();
    String getAuthor();
    String getContent();
    LocalDateTime getCreateAt();
}
