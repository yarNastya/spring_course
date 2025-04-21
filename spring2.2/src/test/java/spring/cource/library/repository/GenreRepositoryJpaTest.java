package spring.cource.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import spring.cource.library.dto.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий для работы с жанрами")
@Import({GenreRepositoryJpa.class})
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepository sut;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Должен добавить новый жанр")
    @Test
    void insert() {
        Genre expectedGenre = new Genre().setName("Сказка");

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