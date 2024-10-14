package spring.cource.spring.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Student {
    private String firstName;
    private String lastName;

    private int correctAnswerCount = 0;

    public void addCorrectAnswer(){
        correctAnswerCount += 1;
    }
}
