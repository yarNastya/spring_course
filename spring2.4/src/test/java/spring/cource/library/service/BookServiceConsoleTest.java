package spring.cource.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.cource.library.dto.Author;
import spring.cource.library.dto.Book;
import spring.cource.library.dto.Genre;
import spring.cource.library.repository.AuthorRepository;
import spring.cource.library.repository.BookRepository;
import spring.cource.library.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@DisplayName("Сервис для работы с книгами")
@ExtendWith(MockitoExtension.class)
class BookServiceConsoleTest {
    @InjectMocks
    private BookServiceConsole sut;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private GenreRepository genreRepository;

    private final long EXPECTED_ID = 25L;

    @Test
    @DisplayName("Должен вернуть количество книг")
    void shouldReturnCount() {
        doReturn(5L).when(bookRepository).count();
        var expected = sut.count();
        assertEquals(expected, 5L);
    }

    @Test
    @DisplayName("Должен вернуть список книг")
    void shouldGetAllBooks() {
        doReturn(List.of(new Book().setId(25), new Book().setId(26))).when(bookRepository).findAll();
        var expected = sut.getAll();
        assertEquals(expected.size(), 2);
    }

    @Test
    @DisplayName("Должен вернуть книгу по id")
    void shouldGetBookById() {
        Author expectedAuthor = new Author().setId(1L).setName("author_name");
        Genre expectedGenre = new Genre().setId(11L);
        Book expectedBook = new Book().setId(EXPECTED_ID).setName("name").setAuthor(expectedAuthor).setGenre(expectedGenre);
        doReturn(Optional.of(expectedBook)).when(bookRepository).findById(EXPECTED_ID);
        String result = sut.getById(EXPECTED_ID);
        assertThat(result).isEqualTo("Найдена книга: id:25, название:name, автор:author_name, год:0\r\n");
    }

    @Test
    @DisplayName("Должен добавить книгу")
    void shouldAddNewBookSucessfully() {
        Author author = new Author().setId(1L);
        Genre genre = new Genre().setId(11);
        doReturn(new Book().setId(EXPECTED_ID)).when(bookRepository).save(any());
        doReturn(Optional.of(author)).when(authorRepository).findById(1L);
        doReturn(Optional.of(genre)).when(genreRepository).findById(11L);
        String result = sut.insertBook("Test", 1L, 11L, 1777);
        assertEquals(result, "Добавлена книга с id = 25");

    }

    @Test
    @DisplayName("Должен не смочь добавить книгу")
    void shouldNotAddNewBook() {
        Author author = new Author().setId(1L);
        Genre genre = new Genre().setId(11);
        doReturn(Optional.empty()).when(bookRepository).save(any());
        doReturn(Optional.of(author)).when(authorRepository).findById(1L);
        doReturn(Optional.of(genre)).when(genreRepository).findById(11L);
        String result = sut.insertBook("Test", 1L, 11L, 1777);
        assertEquals(result, "Не удалось добавить книгу в библиотеку");

    }

    @Test
    @DisplayName("Должен обновить книгу")
    void shouldUpdateBookSucessfully() {
        Author author = new Author().setId(1L);
        Genre genre = new Genre().setId(11);
        Book book = new Book().setId(EXPECTED_ID);
        doReturn(Optional.of(book)).when(bookRepository).findById(EXPECTED_ID);
        doReturn(new Book().setId(EXPECTED_ID)).when(bookRepository).save(any());
        doReturn(Optional.of(author)).when(authorRepository).findById(1L);
        doReturn(Optional.of(genre)).when(genreRepository).findById(11L);
        String result = sut.updateBook(EXPECTED_ID, "Test", 1L, 11L, 1777);
        assertEquals(result, "Данные о книге c id = 25 обновлены");

    }
}