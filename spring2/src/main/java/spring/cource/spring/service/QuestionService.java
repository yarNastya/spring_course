package spring.cource.spring.service;

import spring.cource.spring.domain.Question;

import java.io.BufferedReader;
import java.util.List;

public interface QuestionService {

    List<Question> fillQuestionList();
    void printQuestionList(List<Question> questionList);
    void printQuestion(Question question);
}
