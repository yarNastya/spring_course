package spring.cource.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import spring.cource.spring.domain.Question;
import spring.cource.spring.domain.Student;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.properties")
public class SurveyServiceImpl implements SurveyService {

    private final QuestionService questionService;

    @Override
    public void makeSurvey(int requiredNumber) {
        List<Question> questionList = questionService.fillQuestionList();
        Scanner in = new Scanner(System.in);
        Student student = createStudent(in);
        fillStudentAnswers(student, questionList, in);
        printResult(student, requiredNumber);
    }


    private Student createStudent(Scanner in) {
        Student student = new Student();
        System.out.println("Please, enter your first name");
        if (in.hasNextLine()) {
            student.setFirstName(in.nextLine());
        }
        System.out.println("Please, enter your last name");
        if (in.hasNextLine()) {
            student.setLastName(in.nextLine());
        }
        return student;
    }


    private void fillStudentAnswers(Student student, List<Question> questionList, Scanner in) {
        System.out.println("Please enter the number of the correct answer");

        for (Question question : questionList) {
            printQuestion(question);
            if (in.hasNextInt()) {
                Integer number = in.nextInt();
                if (Objects.equals(number, question.getCorrectAnswerNum())) {
                    student.addCorrectAnswer();
                }
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
            System.out.printf("Correct anwers count are %d. %s %s, you have passed the test.", student.getCorrectAnswerCount(), student.getFirstName(), student.getLastName());
        } else {
            System.out.printf("Correct anwers count are %d. %s %s, you have not passed the test.", student.getCorrectAnswerCount(), student.getFirstName(), student.getLastName());
        }

    }
}
