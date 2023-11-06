package jungle.jungle_week_13.controller;


import jungle.jungle_week_13.dto.CommentRequestDto;
import jungle.jungle_week_13.dto.CommentResponseDto;
import jungle.jungle_week_13.entity.Comment;
import jungle.jungle_week_13.response.BasicResponse;
import jungle.jungle_week_13.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public CommentResponseDto createComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(requestDto);
    }

    @PatchMapping("/comments")
    public CommentResponseDto updateComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.update(requestDto);
    }

    @DeleteMapping("/comments/{comment_id}")
    public ResponseEntity<BasicResponse> deleteComment(@PathVariable Long comment_id) {
        return commentService.delete(comment_id);
    }
}
