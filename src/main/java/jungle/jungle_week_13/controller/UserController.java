package jungle.jungle_week_13.controller;

import jungle.jungle_week_13.dto.UserRequestDto;
import jungle.jungle_week_13.entity.SignupRequestDto;
import jungle.jungle_week_13.response.BasicResponse;
import jungle.jungle_week_13.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BasicResponse> register(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            String errorMessage = errors.stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
            throw new IllegalArgumentException(errorMessage);
        }

        return userService.register(requestDto);
    }

    @PostMapping("/login")
    public ResponseEntity<BasicResponse> login(@RequestBody UserRequestDto dto, HttpServletResponse response) {
        return userService.login(dto, response);
    }
}
