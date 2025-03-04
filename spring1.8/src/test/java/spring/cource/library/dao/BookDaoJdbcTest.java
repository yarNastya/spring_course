package spring.cource.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import spring.cource.library.dto.Author;
import spring.cource.library.dto.Book;
import spring.cource.library.dto.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@DisplayName("Дао для работы с книгами")
@Import({BookDaoJdbc.class,AuthorDaoJdbc.class,GenreDaoJdbc.class})
class BookDaoJdbcTest {


    @Autowired
    private BookDaoJdbc sut;

    private final long EXISTED_ID = 1L;


    @DisplayName("Должен вернуть количество книг в базе")
    @Test
    void shouldReturnBookCount() {

        int actualCount = sut.count();

        assertThat(actualCount).isEqualTo(1);
    }

    @DisplayName("Должен добавить книгу")
    @Test
    void shouldInsertBook() {
        Book expectedBook = new Book().setId(100L).setName("Маскарад").setAuthor(new Author().setId(2))
                .setGenre(new Genre().setId(4)).setYear(1835);

        sut.insert(expectedBook);
        Book actualBook= sut.getById(expectedBook.getId()).get();

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @DisplayName("Должен обновить книгу")
    @Test
    void shouldUpdateBook() {
        String name = "Test Name";
        long authorId = 2L;
        long genreId = 3L;
        int year = 1999;

        Book book = sut.getById(EXISTED_ID).get();
        assertNotEquals(book.getName(),name);
        assertNotEquals(book.getAuthor().getId(),authorId);
        assertNotEquals(book.getGenre().getId(),genreId);
        assertNotEquals(book.getYear(),year);


        book.setName(name);
        book.setAuthor(new Author().setId(authorId));
        book.setGenre(new Genre().setId(genreId));
        book.setYear(year);
        sut.update(book);

        book = sut.getById(EXISTED_ID).get();
        assertEquals(book.getName(),name);
        assertEquals(book.getAuthor().getId(),authorId);
        assertEquals(book.getGenre().getId(),genreId);
        assertEquals(book.getYear(),year);
    }

    @DisplayName("Должен удалить книгу по ид")
    @Test
    void deleteById() {
        assertTrue(sut.getById(EXISTED_ID).isPresent());
        sut.deleteById(EXISTED_ID);
        assertTrue(sut.getById(EXISTED_ID).isEmpty());
    }

    @DisplayName("Должен найти книгу по ид")
    @Test
    void getById() {
        Book expectedBook = new Book().setId(1)
                .setAuthor(new Author().setId(1L))
                .setGenre(new Genre().setId(1L))
                .setName("Полтава").setYear(1828);

        Book actualBook= sut.getById(EXISTED_ID).get();

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @DisplayName("Должен вернуть все книги в базе")
    @Test
    void shouldGetAllBooks() {
        Book expectedBook = new Book().setId(EXISTED_ID).setName("Полтава").setAuthor(new Author().setId(1)).setGenre(new Genre().setId(1)).setYear(1828);

        List<Book> actualList = sut.getAllBooks();

        assertThat(actualList).hasSize(1).contains(expectedBook);

    }
}