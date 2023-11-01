package jungle.jungle_week_13.controller;

import jungle.jungle_week_13.dto.PostRequestDto;
import jungle.jungle_week_13.dto.PostSummary;
import jungle.jungle_week_13.entity.Post;
import jungle.jungle_week_13.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/posts")
    public Post createMemo(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    @GetMapping("/posts")
    public List<PostSummary> getMemos() {
        return postService.getPosts();
    }

}
