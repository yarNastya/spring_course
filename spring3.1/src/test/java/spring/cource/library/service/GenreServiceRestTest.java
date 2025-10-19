package spring.cource.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.cource.library.domain.Genre;
import spring.cource.library.dto.GenreDto;
import spring.cource.library.repository.GenreRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@DisplayName("Сервис для работы с жанрами")
@ExtendWith(MockitoExtension.class)
class GenreServiceRestTest {

    @InjectMocks
    private GenreServiceRest sut;
    @Mock
    private GenreRepository genreRepository;

    @Test
    @DisplayName("Должен добавить жанр")
    void insertGenre() {
        Genre domainGenre = new Genre().setId(100L).setName("name");
        GenreDto expectedGenre = GenreDto.toDto(domainGenre);
        doReturn(domainGenre).when(genreRepository).save(any());

        GenreDto result = sut.insertGenre(expectedGenre.getName());

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedGenre);
    }
}