package spring.cource.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import spring.cource.spring.domain.Question;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для тестирования")
@ExtendWith(MockitoExtension.class)
public class SurveyServiceImplTest {
    @Mock
    private QuestionService questionServiceMock;

    @Mock
    private IOService ioService;

    @InjectMocks
    private SurveyServiceImpl sut;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(sut, "requiredNumber", 4);
    }

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
    @DisplayName("должен засчитывать тест при 5 корректных ответах")
    void shouldPassTestWith5CorrectAnswers() {
        //given
        when(questionServiceMock.fillQuestionList()).thenReturn(questionList);
        doNothing().when(ioService).writeString(any());
        when(ioService.readString()).thenReturn("Ivan", "Ivanov", "1", "1", "1", "1", "1");

        //when
        var result = sut.makeSurvey();

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("не должен засчитывать тест при 4 корректных ответах")
    void shouldPassTestWith4CorrectAnswers() {
        //given
        when(questionServiceMock.fillQuestionList()).thenReturn(questionList);
        doNothing().when(ioService).writeString(any());
        when(ioService.readString()).thenReturn("Ivan", "Ivanov", "1", "1", "1", "1", "1");

        //when
        var result = sut.makeSurvey();

        //then
        assertTrue(result);
    }

    @Test
    @DisplayName("не должен засчитывать тест при 3 корректных ответах")
    void shouldNotPassTestWithThreeAnswers() {
        //given
        when(questionServiceMock.fillQuestionList()).thenReturn(questionList);
        when(ioService.readString()).thenReturn("Ivan", "Ivanov", "1", "1", "1", "2", "3");

        //when
        var result = sut.makeSurvey();

        //then
        assertFalse(result);
    }

    @Test
    @DisplayName("не должен засчитывать тест при 0 корректных ответов")
    void shouldNotPassTestWithZeroAnswers() {
        //given
        when(questionServiceMock.fillQuestionList()).thenReturn(questionList);
        when(ioService.readString()).thenReturn("Ivan", "Ivanov", "2", "3", "3", "2", "3");

        //when
        var result = sut.makeSurvey();

        //then
        assertFalse(result);
    }
}
