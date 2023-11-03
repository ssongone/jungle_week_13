package jungle.jungle_week_13.service;

import jungle.jungle_week_13.dto.PostRequestDto;
import jungle.jungle_week_13.dto.PostRespondDto;
import jungle.jungle_week_13.dto.PostSummary;
import jungle.jungle_week_13.entity.Post;
import jungle.jungle_week_13.entity.User;
import jungle.jungle_week_13.jwt.JwtUtil;
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
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<BasicResponse> createPost(User user) {
        Optional<User> byUserId = userRepository.findByUserId(user.getUserId());
        if (byUserId.isEmpty())
            userRepository.save(user);
        else
            throw new IllegalArgumentException("중복된 사용자 아이디 입니다");
        BasicResponse response = new BasicResponse(HttpStatus.CREATED, "회원 등록 완료되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @Transactional
    public ResponseEntity<BasicResponse> login(User user, HttpServletResponse response) {
        Optional<User> byUserId = userRepository.findByUserId(user.getUserId());

        if (byUserId.isEmpty())
            throw new IllegalArgumentException("로그인 정보를 확인해주세요");

        User target = byUserId.get();
        if (!target.getPassword().equals(user.getPassword()))
            throw new IllegalArgumentException("로그인 정보를 확인해주세요");

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUserId()));
        BasicResponse gg = new BasicResponse(HttpStatus.OK, "로그인 성공");
        return new ResponseEntity<>(gg, HttpStatus.OK);
    }
}
