package spring.cource.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.cource.library.dto.Genre;
import spring.cource.library.repository.GenreRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@DisplayName("Сервис для работы с жанрами")
@ExtendWith(MockitoExtension.class)
class GenreServiceConsoleTest {

    @InjectMocks
    private GenreServiceConsole sut;
    @Mock
    private GenreRepository genreRepository;
    @Test
    @DisplayName("Должен добавить жанр")
    void insertGenre() {
        Genre expectedGenre = new Genre().setId(100L).setName("name");
        doReturn(expectedGenre).when(genreRepository).save(any());
        String result = sut.insertGenre(expectedGenre.getName());
        assertEquals(result, "Добавлен новый жанр с id = 100");
    }
}