package jungle.jungle_week_13.service;

import jungle.jungle_week_13.dto.PostRespondDto;
import jungle.jungle_week_13.dto.PostSummary;
import jungle.jungle_week_13.dto.PostRequestDto;
import jungle.jungle_week_13.entity.Post;
import jungle.jungle_week_13.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

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

    @Transactional
    public PostRespondDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        if (!post.getPassword().equals(requestDto.getPassword()))
            throw new IllegalArgumentException("비밀번호가 틀렸습니다");

        post.update(requestDto);

        return post.convertToDto();
    }

    @Transactional
    public void deletePost(Long id, String password) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        if (!post.getPassword().equals(password))
            throw new IllegalArgumentException("비밀번호가 틀렸습니다");

        postRepository.deleteById(id);
    }





}
