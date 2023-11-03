package jungle.jungle_week_13.controller;

import jungle.jungle_week_13.entity.User;
import jungle.jungle_week_13.response.BasicResponse;
import jungle.jungle_week_13.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<BasicResponse> createUser(@Valid @RequestBody User user) {
        return userService.createPost(user);
    }

    @PostMapping("/login")
    public ResponseEntity<BasicResponse> login(@RequestBody User user, HttpServletResponse response) {
        return userService.login(user, response);
    }
}
