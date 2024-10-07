package spring.cource.spring.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.cource.spring.domain.Question;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Сервис для работы с вопросам")
@ExtendWith(MockitoExtension.class)
public class QustionServiceImplTest {

    @InjectMocks
    private QuestionServiceImpl sut;

    @Mock
    private IOService ioService;

    @Mock
    private FileService fileService;

    @Test
    @DisplayName("должен заполнять список вопросов")
    void shouldFillQuestionList() {
        //given
        InputStream anyInputStream = new ByteArrayInputStream("Question;1;answer1,answer2, answer3".getBytes());
        InputStreamReader streamReader = new InputStreamReader(anyInputStream, StandardCharsets.UTF_8);
        when(fileService.readFile()).thenReturn(new BufferedReader(streamReader));

        //when
        List<Question> result = sut.fillQuestionList();

        //then
        assertEquals(result.size(), 1);
    }
}
