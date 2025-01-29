package spring.cource.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Student {
    private String firstName;
    private String lastName;

    private int correctAnswerCount = 0;

    public void addCorrectAnswer() {
        correctAnswerCount += 1;
    }
}
