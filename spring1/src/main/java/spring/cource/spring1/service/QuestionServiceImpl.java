package spring.cource.spring1.service;

import spring.cource.spring1.domain.Question;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class QuestionServiceImpl implements QuestionService{

    public List<Question> fillQuestionList(BufferedReader reader){
        List<Question> questionList = new ArrayList<>();
        try{
        for (String line; (line = reader.readLine()) != null; ) {
            String[] phrases = line.split(";");
            Question question = new Question(phrases[0]);

            for (int i = 1; i < phrases.length; i++) {
                question.addAnswerOption(phrases[i]);
            }
            questionList.add(question);
        }}
        catch(Exception exp)
            {
                System.out.println(exp.getMessage());
        }
        return questionList;

    }

    public void printQuestionList(List<Question> questionList)
    {
        for (Question question : questionList) {
            System.out.println(question.getQuestionText());
            question.getAnswerOptionList().forEach(System.out::println);
            System.out.println();
        }
    }
}
