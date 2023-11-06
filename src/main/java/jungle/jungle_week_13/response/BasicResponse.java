package jungle.jungle_week_13.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicResponse {
    private int code;
    private String message;
}
