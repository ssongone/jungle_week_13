package jungle.jungle_week_13.service;

import jungle.jungle_week_13.dto.PostRequestDto;
import jungle.jungle_week_13.dto.PostRespondDto;
import jungle.jungle_week_13.dto.PostSummary;
import jungle.jungle_week_13.entity.Post;
import jungle.jungle_week_13.entity.SignupRequestDto;
import jungle.jungle_week_13.entity.User;
import jungle.jungle_week_13.entity.UserRoleEnum;
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
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public ResponseEntity<BasicResponse> register(SignupRequestDto dto) {
        String newId = dto.getUserId();
        String newPw = dto.getPassword();

        Optional<User> byUserId = userRepository.findByUserId(newId);
        if (byUserId.isPresent())
            throw new IllegalArgumentException("중복된 사용자 아이디 입니다");

        UserRoleEnum role = UserRoleEnum.USER;
        if (dto.isAdmin()) {
            if (!dto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        User user = new User(newId, newPw, role);
        userRepository.save(user);
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

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(target.getUserId(), target.getRole()));
        BasicResponse gg = new BasicResponse(HttpStatus.OK, "로그인 성공");
        return new ResponseEntity<>(gg, HttpStatus.OK);
    }
}
