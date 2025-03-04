package spring.cource.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import spring.cource.library.dto.Author;
import spring.cource.library.dto.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DisplayName("Дао для работы с жанрами")
@Import({GenreDaoJdbc.class})
class GenreDaoJdbcTest {

    @Autowired
    private GenreDao sut;

    @DisplayName("Должен добавить новый жанр")
    @Test
    void insert() {
        Genre expectedGenre = new Genre().setId(100L).setName("Сказка");

        sut.insert(expectedGenre);

        Genre actualGenre = sut.getById(expectedGenre.getId()).get();

        assertThat(actualGenre).usingRecursiveComparison().isEqualTo(expectedGenre);
    }

    @DisplayName("Должен найти жанр по ид")
    @Test
    void getById() {
        Genre expectedGenre = new Genre().setId(1L).setName("Поэма");

        Genre actualGenre = sut.getById(1).get();

        assertThat(actualGenre).isEqualTo(expectedGenre);
    }
}