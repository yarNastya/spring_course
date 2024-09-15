package spring.cource.spring1.service;

import spring.cource.spring1.domain.Question;

import java.io.BufferedReader;
import java.util.List;

public interface QuestionService {

    List<Question> fillQuestionList(BufferedReader reader);
    void printQuestionList(List<Question> questionList);
}
