package jungle.jungle_week_13.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postId;
    private Long commenterId;
    private String content;
}
