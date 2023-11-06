package jungle.jungle_week_13.exception;

public class InvalidJwtException extends IllegalArgumentException {
    public InvalidJwtException() {
        super();
    }

    public InvalidJwtException(String message) {
        super(message);
    }

    public InvalidJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
