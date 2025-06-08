package spring.cource.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import spring.cource.library.dto.Author;
import spring.cource.library.dto.Book;
import spring.cource.library.dto.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
@DisplayName("Репозиторий для работы с книгами")
class BookRepositoryTest {
    @Autowired
    private BookRepository sut;
    @Autowired
    private TestEntityManager em;

    private final long EXISTED_ID = 1L;

    @DisplayName("Должен вернуть количество книг в базе")
    @Test
    void shouldReturnBookCount() {

        long actualCount = sut.count();

        assertThat(actualCount).isEqualTo(1L);
    }

    @DisplayName("Должен добавить книгу")
    @Test
    void shouldInsertBook() {
        Author author = em.find(Author.class, 1L);
        Genre genre = em.find(Genre.class, 1L);
        Book expectedBook = new Book().setName("Test").setAuthor(author)
                .setGenre(genre).setYear(1835);
        sut.save(expectedBook);
        Book actualBook = sut.findById(expectedBook.getId()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @DisplayName("Должен обновить книгу")
    @Test
    void shouldUpdateBook() {
        String name = "Test Name";
        long authorId = 2L;
        long genreId = 3L;
        int year = 1999;

        Book book = sut.findById(EXISTED_ID).get();
        assertNotEquals(book.getName(), name);
        assertNotEquals(book.getAuthor().getId(), authorId);
        assertNotEquals(book.getGenre().getId(), genreId);
        assertNotEquals(book.getYear(), year);


        book.setName(name);
        book.setAuthor(new Author().setId(authorId));
        book.setGenre(new Genre().setId(genreId));
        book.setYear(year);
        sut.save(book);

        book = sut.findById(EXISTED_ID).get();
        assertEquals(book.getName(), name);
        assertEquals(book.getAuthor().getId(), authorId);
        assertEquals(book.getGenre().getId(), genreId);
        assertEquals(book.getYear(), year);
    }

    @DisplayName("Должен удалить книгу по ид")
    @Test
    void deleteById() {
        Book book1 = em.find(Book.class, EXISTED_ID);
        assertThat(book1).isNotNull();
        sut.deleteById(EXISTED_ID);
        sut.flush();
        Book book2 = em.find(Book.class, EXISTED_ID);
        assertThat(book2).isNull();
    }

    @DisplayName("Должен найти книгу по ид")
    @Test
    void getById() {
        Book expectedBook = em.find(Book.class, EXISTED_ID);

        Book actualBook = sut.findById(EXISTED_ID).get();

        assertThat(actualBook).usingRecursiveComparison().isEqualTo(expectedBook);

    }

    @DisplayName("Должен вернуть все книги в базе")
    @Test
    void shouldGetAllBooks() {
        Book expectedBook = em.find(Book.class, EXISTED_ID);

        List<Book> actualList = sut.findAll();

        assertThat(actualList).hasSize(1).contains(expectedBook);

    }
}