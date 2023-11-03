package jungle.jungle_week_13.service;

import jungle.jungle_week_13.dto.PostRequestDto;
import jungle.jungle_week_13.dto.PostRespondDto;
import jungle.jungle_week_13.dto.PostSummary;
import jungle.jungle_week_13.entity.Post;
import jungle.jungle_week_13.entity.User;
import jungle.jungle_week_13.repository.PostRepository;
import jungle.jungle_week_13.repository.UserRepository;
import jungle.jungle_week_13.response.ApiResponse;
import jungle.jungle_week_13.response.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public ResponseEntity<BasicResponse> createPost(User user) {
        Optional<User> byUserId = userRepository.findByUserId(user.getUserId());
        System.out.println(byUserId);
        if (byUserId.isEmpty())
            userRepository.save(user);
        else
            throw new IllegalArgumentException("중복된 사용자 아이디 입니다");
        BasicResponse response = new BasicResponse(HttpStatus.CREATED, "회원 등록 완료되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


//    public ResponseEntity<BasicResponse> login(User user) {
//
//    }
}
