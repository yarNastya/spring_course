package spring.cource.library.exception;

import org.springframework.stereotype.Component;

@Component
public class CouldNotSaveException extends RuntimeException{
    private String message;
    public CouldNotSaveException() {
        super();
    }

    public CouldNotSaveException(String message) {
        super(message);
    }
}
