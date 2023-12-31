package jungle.jungle_week_13.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class ApiResponse<T> extends BasicResponse {
    private final T data;

    public ApiResponse(T data, int code, String message) {
        super(code, message);
        this.data = data;
    }
}
