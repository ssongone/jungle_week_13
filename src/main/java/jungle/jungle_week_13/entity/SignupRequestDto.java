package jungle.jungle_week_13.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SignupRequestDto {
    //최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)
    @Column(nullable = false)
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "아이디는 4자 이상 10자 이하여야 하고 알파벳 소문자와 숫자만 가능합니다")
    private String userId;

    //최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9)
    @Column(nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 8자 이상 15자 이하여야 하고 알파벳 대소문자와 숫자만 가능합니다")
    private String password;

    private boolean admin = false;
    private String adminToken = "";
}