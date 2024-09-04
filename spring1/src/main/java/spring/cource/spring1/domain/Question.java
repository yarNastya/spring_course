package spring.cource.spring1.domain;

import java.util.ArrayList;
import java.util.List;

public class Question {
    private final String questionText;
    private List<String> answerOptionList = new ArrayList<>();
    private String answerText;


    public Question(String questionText) {

        this.questionText = questionText;
    }


    public String getQuestionText() {
        return questionText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAnswerTex() {
        return answerText;
    }

    public List<String> getAnswerOptionList() {
        return this.answerOptionList;
    }

    public void setAnswerOptionList(List<String> answerOptionList) {
        this.answerOptionList = answerOptionList;
    }

    public void addAnswerOption(String answerOption) {
        this.answerOptionList.add(answerOption);
    }
}
