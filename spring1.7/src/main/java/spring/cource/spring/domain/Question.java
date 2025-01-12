package spring.cource.spring.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Accessors(chain = true)
public class Question {
    private final String questionText;
    private Integer correctAnswerNum;
    private List<String> answerOptionList = new ArrayList<>();

    public Question(String questionText) {
        this.questionText = questionText;
    }

    public void addAnswerOption(String answerOption) {
        this.answerOptionList.add(answerOption);
    }
}
