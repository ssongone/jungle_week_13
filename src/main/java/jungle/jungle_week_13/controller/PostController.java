package jungle.jungle_week_13.controller;

import jungle.jungle_week_13.dto.PostRequestDto;
import jungle.jungle_week_13.dto.PostRespondDto;
import jungle.jungle_week_13.dto.PostSummary;
import jungle.jungle_week_13.entity.Post;
import jungle.jungle_week_13.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public List<PostSummary> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/posts/{id}")
    public PostRespondDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

}
