package jungle.jungle_week_13.service;

import jungle.jungle_week_13.dto.PostRespondDto;
import jungle.jungle_week_13.dto.PostSummary;
import jungle.jungle_week_13.dto.PostRequestDto;
import jungle.jungle_week_13.entity.Post;
import jungle.jungle_week_13.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post createPost(PostRequestDto requestDto) {
        Post post = new Post(requestDto);
        postRepository.save(post);
        return post;
    }

    @Transactional
    public List<PostSummary> getPosts() {
        return postRepository.findAllByOrderByCreateAtDesc();
    }

    @Transactional
    public PostRespondDto getPost(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        return post.convertToDto();
    }


}
