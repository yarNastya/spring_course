package spring.cource.spring.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Сервис для ввода/вывода")
@ExtendWith(MockitoExtension.class)
public class IOServiceImplTest {

    @InjectMocks
    private IOServiceImpl sut;

    @Test
    @DisplayName("должен считывать строку")
    void shouldReadString() {
        //given
        ByteArrayInputStream in = new ByteArrayInputStream("Test".getBytes());
        System.setIn(in);

        //when
        String result = sut.readString();

        //then
        assertEquals(result, "Test");
    }

    @Test
    @DisplayName("должен выводить строку")
    void shouldWriteString() {
        //given
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        //when
        sut.writeString("Test");

        //then
        assertTrue(out.toString().contains("Test"));
    }
}
