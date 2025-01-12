package spring.cource.spring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import spring.cource.spring.domain.Question;
import spring.cource.spring.domain.Student;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:application.yml")
public class SurveyServiceImpl implements SurveyService {

    @Value("${requiredNumber}")
    private int requiredNumber;
    private final QuestionService questionService;
    private final IOService ioService;
    private final MessageSource messageSource;
    private final Locale locale = Locale.getDefault();

    @Override
    public boolean makeSurvey(Student student) {
        List<Question> questionList = questionService.fillQuestionList();
        //    Student student = createStudent();
        fillStudentAnswers(student, questionList);
        printResult(student, requiredNumber);
        return student.getCorrectAnswerCount() >= requiredNumber;
    }


    private Student createStudent() {
        Student student = new Student();
        ioService.writeString(messageSource.getMessage("firstNameQuestion", null, locale));
        student.setFirstName(ioService.readString());
        ioService.writeString(messageSource.getMessage("lastNameQuestion", null, locale));
        student.setLastName(ioService.readString());

        return student;
    }


    private void fillStudentAnswers(Student student, List<Question> questionList) {
        ioService.writeString(messageSource.getMessage("askEnterAnswers", null, locale));
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
                    String.format(messageSource.getMessage("goodResultMessage", new Object[]{student.getCorrectAnswerCount(), student.getFirstName(), student.getLastName()}, locale),
                            student.getCorrectAnswerCount(), student.getFirstName(), student.getLastName()));

        } else {
            ioService.writeString(messageSource.getMessage("badResultMessage", new Object[]{student.getCorrectAnswerCount(), student.getFirstName(), student.getLastName()}, locale)
            );
        }

    }
}
