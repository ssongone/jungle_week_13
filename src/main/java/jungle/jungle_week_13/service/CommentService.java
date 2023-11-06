package jungle.jungle_week_13.service;

import jungle.jungle_week_13.dto.CommentRequestDto;
import jungle.jungle_week_13.entity.Comment;
import jungle.jungle_week_13.entity.Post;
import jungle.jungle_week_13.repository.CommentRepository;
import jungle.jungle_week_13.repository.PostRepository;
import jungle.jungle_week_13.repository.UserRepository;
import jungle.jungle_week_13.response.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(CommentRequestDto requestDto) {
        Long postId = requestDto.getPostId();

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User currentUser = (User) authentication.getPrincipal();
            Comment comment = new Comment(post, userRepository.findByUserId(currentUser.getUsername()).get(), requestDto.getContent());
            commentRepository.save(comment);

            return comment;
        } else
            throw new IllegalArgumentException("사용자 정보를 가져올 수 없습니다.");

    }

    @Transactional
    public Comment update(CommentRequestDto requestDto) {
        Long commentId = requestDto.getPostId(); //사실 넘어오는 거 commentId임

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
            if (!isAdmin) {
                User currentUser = (User) authentication.getPrincipal();

                Long userIdByToken = userRepository.findByUserId(currentUser.getUsername()).get().getId();

                Long userIdByComment = comment.getCommenter().getId();

//                throw new AccessDeniedException("Access is denied");
                if (userIdByToken != userIdByComment)
                    throw new IllegalArgumentException("Access is denied");
            }

            comment.update(requestDto);
            return comment;
        } else
            throw new IllegalArgumentException("사용자 정보를 가져올 수 없습니다.");

    }
    @Transactional
    public ResponseEntity<BasicResponse> delete(Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
            if (!isAdmin) {
                User currentUser = (User) authentication.getPrincipal();

                Long userIdByToken = userRepository.findByUserId(currentUser.getUsername()).get().getId();

                Long userIdByComment = comment.getCommenter().getId();

//                throw new AccessDeniedException("Access is denied");
                if (userIdByToken != userIdByComment)
                    throw new IllegalArgumentException("Access is denied");
            }

            commentRepository.deleteById(commentId);
            BasicResponse response = new BasicResponse(HttpStatus.NO_CONTENT, "삭제 완료");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } else
            throw new IllegalArgumentException("사용자 정보를 가져올 수 없습니다.");

    }
}
