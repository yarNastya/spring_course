package spring.cource.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import spring.cource.library.dto.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий для работы с авторами")
@Import({AuthorRepositoryJpa.class})
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepositoryJpa sut;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Должен найти автора по Id")
    @Test
    void getById() {
        Author expectedAuthor = new Author().setId(1L).setName("Александр").setPatronymic("Сергеевич").setSurname("Пушкин");

        Author actualAuthor = sut.getById(1).get();

        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName("Должен добавить нового автора")
    @Test
    void insert() {
        Author expectedAuthor = new Author().setName("Лев").setPatronymic("Николаевич").setSurname("Толстой");
        sut.insert(expectedAuthor);

        Author actualAuthor = sut.getById(expectedAuthor.getId()).get();

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}