package jungle.jungle_week_13.service;

import jungle.jungle_week_13.dto.UserRequestDto;
import jungle.jungle_week_13.entity.SignupRequestDto;
import jungle.jungle_week_13.entity.User;
import jungle.jungle_week_13.jwt.JwtTokenProvider;
import jungle.jungle_week_13.jwt.TokenInfo;
import jungle.jungle_week_13.repository.UserRepository;
import jungle.jungle_week_13.response.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    // ADMIN_TOKEN
    private static final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
//    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public ResponseEntity<BasicResponse> register(SignupRequestDto dto) {
        String newId = dto.getUserId();
        String newPw = dto.getPassword();

        Optional<User> byUserId = userRepository.findByUserId(newId);
        if (byUserId.isPresent())
            throw new IllegalArgumentException("중복된 사용자 아이디 입니다");

        String role = "ROLE_USER";
        if (dto.isAdmin()) {
            if (!dto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = "ROLE_ADMIN";
        }
        String encodedPassword = passwordEncoder.encode(newPw);
        User user = new User(newId, encodedPassword, role);
        userRepository.save(user);
        BasicResponse response = new BasicResponse(HttpStatus.CREATED.value(), "회원 등록 완료되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<BasicResponse> login(UserRequestDto dto, HttpServletResponse response) {
        String nowId = dto.getUserId();
        String nowPw = dto.getPassword();
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(nowId, nowPw);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        response.addHeader("Authorization", "Bearer " + tokenInfo.getAccessToken());

        BasicResponse gg = new BasicResponse(HttpStatus.OK.value(), "로그인 성공");
        return new ResponseEntity<>(gg, HttpStatus.OK);
    }
}
