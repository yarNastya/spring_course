package spring.cource.library.exception;


import org.springframework.stereotype.Component;

@Component
public class NotFoundException extends RuntimeException{


    public NotFoundException(){
        super();
    }

    public NotFoundException(String message){
        super(message);
    }
}
