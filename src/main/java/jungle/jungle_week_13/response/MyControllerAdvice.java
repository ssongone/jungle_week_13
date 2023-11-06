package jungle.jungle_week_13.response;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jungle.jungle_week_13.exception.InvalidJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class MyControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public BasicResponse illegalExHandle(IllegalArgumentException e) {
        log.error("IllegalArgumentException ", e);
        return new BasicResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public BasicResponse exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new BasicResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "내부 오류");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BasicResponse handleValidationExceptions(MethodArgumentNotValidException e) {
        return new BasicResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadCredentialsException.class)
    public BasicResponse handleBadCredentialsException(BadCredentialsException e) {
        return new BasicResponse(HttpStatus.BAD_REQUEST.value(), "회원을 찾을 수 없습니다");
    }


//    @ExceptionHandler
//    public ResponseEntity<BasicResponse> userExHandle(ExecutionControl.UserException e) {
//        log.error("[exceptionHandle] ex", e);
//        BasicResponse errorResult = new BasicResponse("USER-EX", e.getMessage());
//        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
//    }

}
