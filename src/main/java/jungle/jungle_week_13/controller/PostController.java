package jungle.jungle_week_13.controller;

import jungle.jungle_week_13.dto.PostRequestDto;
import jungle.jungle_week_13.dto.PostResponseDto;
import jungle.jungle_week_13.entity.Post;
import jungle.jungle_week_13.response.BasicResponse;
import jungle.jungle_week_13.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public List<PostResponseDto> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }
    @PatchMapping("/posts/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<BasicResponse> deletePost(@PathVariable Long id) {
        return postService.deletePost(id);
    }
}
