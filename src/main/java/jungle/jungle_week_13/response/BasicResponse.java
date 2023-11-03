package jungle.jungle_week_13.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class BasicResponse {
    private HttpStatus code;
    private String message;
}
