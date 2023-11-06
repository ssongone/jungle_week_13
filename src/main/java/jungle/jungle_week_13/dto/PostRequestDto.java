package jungle.jungle_week_13.dto;

import jungle.jungle_week_13.entity.User;
import lombok.Getter;

@Getter
public class PostRequestDto {
    private String title;
    private String content;
}
