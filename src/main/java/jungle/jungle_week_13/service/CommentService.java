package jungle.jungle_week_13.service;

import jungle.jungle_week_13.dto.CommentRequestDto;
import jungle.jungle_week_13.entity.Comment;
import jungle.jungle_week_13.entity.Post;
import jungle.jungle_week_13.entity.User;
import jungle.jungle_week_13.repository.CommentRepository;
import jungle.jungle_week_13.repository.PostRepository;
import jungle.jungle_week_13.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment createComment(CommentRequestDto requestDto) {
        Long postId = requestDto.getPostId();
        Long userId = requestDto.getCommenterId();

        System.out.println(postId + userId);

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found with id: " + postId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        Comment comment = new Comment(post, user, requestDto.getContent());

        commentRepository.save(comment);

        return comment;
    }
}
