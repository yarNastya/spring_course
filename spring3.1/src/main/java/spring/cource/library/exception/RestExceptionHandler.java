package spring.cource.library.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(CouldNotSaveException.class)
    public ResponseEntity<String> handleSaveDBException(CouldNotSaveException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
