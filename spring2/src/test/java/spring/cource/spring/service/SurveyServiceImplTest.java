package spring.cource.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.cource.spring.domain.Question;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceImplTest {
    @Mock
    private QuestionService questionServiceMock;

    @InjectMocks
    private SurveyServiceImpl sut;

    private final List<Question> questionList = List.of(new Question("q1")
                    .setCorrectAnswerNum(1)
                    .setAnswerOptionList(List.of("a1", "a2", "a3")),
            new Question("q2")
                    .setCorrectAnswerNum(1)
                    .setAnswerOptionList(List.of("a1", "a2", "a3")),
            new Question("q3")
                    .setCorrectAnswerNum(1)
                    .setAnswerOptionList(List.of("a1", "a2", "a3")),
            new Question("q4")
                    .setCorrectAnswerNum(1)
                    .setAnswerOptionList(List.of("a1", "a2", "a3")),
            new Question("q5")
                    .setCorrectAnswerNum(1)
                    .setAnswerOptionList(List.of("a1", "a2", "a3"))
    );

    @Test
    void shouldReturnSucess() {
        //given
        ByteArrayInputStream in = new ByteArrayInputStream("Test\r\nTestov\r\n1\r\n1\r\n1\r\n1\r\n\1".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        when(questionServiceMock.fillQuestionList()).thenReturn(questionList);

        //when
        sut.makeSurvey(4);

        //then
        assertTrue(out.toString().contains("you have passed the test"));
    }


    @Test
    void shouldReturnFalse() {
        //given
        ByteArrayInputStream in = new ByteArrayInputStream("Test\r\nTestov\r\n2\r\n3\r\n3\r\n3\r\n\3".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
        when(questionServiceMock.fillQuestionList()).thenReturn(questionList);

        //when
        sut.makeSurvey(4);

        //then
        assertTrue(out.toString().contains("you have not passed the test"));
    }
}
