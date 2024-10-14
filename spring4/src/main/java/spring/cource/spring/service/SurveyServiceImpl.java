package spring.cource.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import spring.cource.spring.domain.Question;
import spring.cource.spring.domain.Student;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class SurveyServiceImpl implements SurveyService {

    @Value("${requiredNumber}")
    private int requiredNumber;
    private final QuestionService questionService;

    private final IOService ioService;

    @Override
    public boolean makeSurvey() {
        List<Question> questionList = questionService.fillQuestionList();
        Student student = createStudent();
        fillStudentAnswers(student, questionList);
        printResult(student, requiredNumber);
        return student.getCorrectAnswerCount() >= requiredNumber;
    }


    private Student createStudent() {
        Student student = new Student();
        ioService.writeString("Please, enter your first name");
        student.setFirstName(ioService.readString());
        ioService.writeString("Please, enter your last name");
        student.setLastName(ioService.readString());

        return student;
    }


    private void fillStudentAnswers(Student student, List<Question> questionList) {
        ioService.writeString("Please enter the number of the correct answer");

        for (Question question : questionList) {
            printQuestion(question);
            Integer number = Integer.valueOf(ioService.readString());
            if (Objects.equals(number, question.getCorrectAnswerNum())) {
                student.addCorrectAnswer();
            }
        }
    }

    private void printQuestion(Question question) {
        System.out.println(question.getQuestionText());
        question.getAnswerOptionList().forEach(System.out::println);
        System.out.println();

    }

    private void printResult(Student student, int requiredNumber) {
        if (student.getCorrectAnswerCount() >= requiredNumber) {
            ioService.writeString(
                    String.format("Correct anwers count are %d. %s %s, you have passed the test.",
                            student.getCorrectAnswerCount(), student.getFirstName(), student.getLastName()));

        } else {
            ioService.writeString(String.format("Correct anwers count are %d. %s %s, you have not passed the test.",
                    student.getCorrectAnswerCount(), student.getFirstName(), student.getLastName()));
        }

    }
}
