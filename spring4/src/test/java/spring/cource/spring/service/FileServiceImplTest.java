package spring.cource.spring.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.BufferedReader;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Сервис для считывания файла")
@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {

    @InjectMocks
    private FileServiceImpl sut;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(sut, "fileName", "questions3.csv");
    }

    @Test
    @DisplayName("должен заполнить BufferedReader")
    void shouldReadFile() {
        //when
        BufferedReader result = sut.readFile();

        //then
        assertNotNull(result);
        assertNotEquals(result.lines().count(), 0);
    }
}
