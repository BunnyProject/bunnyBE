package bunny.backend.exception;

import bunny.backend.common.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class BunnyExceptionControllerAdivce {
    @ExceptionHandler(BunnyException.class)
    public ResponseEntity<ApiResponse<?>> handlePlanearException(BunnyException e) {
        log.warn("BunnyException", e);
        return ResponseEntity.status(e.getHttpStatus())
                .body(ApiResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationExceptions(MethodArgumentNotValidException e){
        log.error("Valid Exception");
        return ResponseEntity.status(e.getStatusCode())
                .body(ApiResponse.fail(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        log.error("Exception", e);
        return ResponseEntity.status(500)
                .body(ApiResponse.fail(e.getMessage()));
    }
}
