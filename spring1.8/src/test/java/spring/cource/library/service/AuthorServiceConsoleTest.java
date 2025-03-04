package spring.cource.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.cource.library.dao.AuthorDao;
import spring.cource.library.dto.Author;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@DisplayName("Сервис для работы с авторами")
@ExtendWith(MockitoExtension.class)
class AuthorServiceConsoleTest {

    @Mock
    private AuthorDao authorDao;
    @InjectMocks
    private AuthorServiceConsole sut;

    @Test
    @DisplayName("Должен добавить автора")
    void insertAuthor() {
        Author expectedAuthor = new Author().setId(100L).setName("name").setPatronymic("pat").setSurname("sur");
        doReturn(Optional.of(expectedAuthor.getId())).when(authorDao).insert(any());
        String result = sut.insertAuthor(expectedAuthor.getName(),expectedAuthor.getPatronymic(),expectedAuthor.getSurname());
        assertEquals(result,"Добавлен новый автор с id = 100");
    }
}