package jungle.jungle_week_13.response;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
        return new BasicResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public BasicResponse exHandle(Exception e) {
        log.error("[exceptionHandle] ex", e);
        return new BasicResponse(HttpStatus.INTERNAL_SERVER_ERROR, "내부 오류");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BasicResponse handleValidationExceptions(MethodArgumentNotValidException e) {
        return new BasicResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

//    @ExceptionHandler
//    public ResponseEntity<BasicResponse> userExHandle(ExecutionControl.UserException e) {
//        log.error("[exceptionHandle] ex", e);
//        BasicResponse errorResult = new BasicResponse("USER-EX", e.getMessage());
//        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
//    }

}
