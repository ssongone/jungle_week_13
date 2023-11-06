package jungle.jungle_week_13.service;

import jungle.jungle_week_13.dto.PostResponseDto;
import jungle.jungle_week_13.dto.PostRequestDto;
import jungle.jungle_week_13.entity.Post;
import jungle.jungle_week_13.exception.UnauthorizedAccessException;
import jungle.jungle_week_13.repository.PostRepository;
import jungle.jungle_week_13.repository.UserRepository;
import jungle.jungle_week_13.response.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post createPost(PostRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            User currentUser = (User) authentication.getPrincipal();

            jungle.jungle_week_13.entity.User userByToken = userRepository.findByUserId(currentUser.getUsername()).get();

            Post post = new Post(requestDto.getTitle(), userByToken, requestDto.getContent());

            postRepository.save(post);
            return post;

        } else {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다");
        }

    }

    @Transactional
    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreateAtDesc().stream()
                .map(post -> post.convertToDto())
                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        return post.convertToDto();
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            User currentUser = (User) authentication.getPrincipal();

            jungle.jungle_week_13.entity.User userByToken = userRepository.findByUserId(currentUser.getUsername()).get();

            boolean isAdmin = authentication.getAuthorities().stream()
                        .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
            if (!isAdmin) {
                if (post.getAuthor().getId() != userByToken.getId())
                    throw new UnauthorizedAccessException("작성자만 수정할 수 있습니다");
            }

            post.update(requestDto);

            return post.convertToDto();

        } else {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다");
        }

    }

    @Transactional
    public ResponseEntity<BasicResponse> deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
            if (!isAdmin) {
                User currentUser = (User) authentication.getPrincipal();

                Long userIdByToken = userRepository.findByUserId(currentUser.getUsername()).get().getId();

                Long userIdByPost = post.getAuthor().getId();

                if (userIdByToken != userIdByPost)
                    throw new UnauthorizedAccessException("작성자만 삭제할 수 있습니다.");
            }
            postRepository.deleteById(id);
            BasicResponse response = new BasicResponse(HttpStatus.NO_CONTENT.value(), "삭제 완료");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        } else
            throw new IllegalArgumentException("토큰이 유효하지 않습니다");

    }





}
