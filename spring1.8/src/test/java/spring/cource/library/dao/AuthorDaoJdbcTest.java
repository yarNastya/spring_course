package spring.cource.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import spring.cource.library.dto.Author;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@DisplayName("Дао для работы с авторами")
@Import({AuthorDaoJdbc.class})
class AuthorDaoJdbcTest {

    @Autowired
    private AuthorDao sut;

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
        Author expectedAuthor = new Author().setId(100L).setName("Лев").setPatronymic("Николаевич").setSurname("Толстой");

        sut.insert(expectedAuthor);

        Author actualAuthor = sut.getById(expectedAuthor.getId()).get();

        assertThat(actualAuthor).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}