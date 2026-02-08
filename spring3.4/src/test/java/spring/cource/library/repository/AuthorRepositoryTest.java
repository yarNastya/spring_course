package spring.cource.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import spring.cource.library.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@DisplayName("Репозиторий для работы с авторами")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository sut;
    @Autowired
    private TestEntityManager em;

    @DisplayName("Должен найти автора по Id")
    @Test
    void getById() {
        Author expectedAuthor = new Author().setId(1L).setName("Александр").setPatronymic("Сергеевич").setSurname("Пушкин");

        Author actualAuthor = sut.findById(1L).get();

        assertThat(actualAuthor).isEqualTo(expectedAuthor);
    }

    @DisplayName("Должен добавить нового автора")
    @Test
    void insert() {
        Author expectedAuthor = new Author().setName("Лев").setPatronymic("Николаевич").setSurname("Толстой");
        sut.save(expectedAuthor);

        Author actualAuthor = sut.findById(expectedAuthor.getId()).get();

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}