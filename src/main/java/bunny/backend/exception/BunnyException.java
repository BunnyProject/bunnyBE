package bunny.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BunnyException extends RuntimeException {
    private final String message;
    private final HttpStatus httpStatus;

    public BunnyException(String message,HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
