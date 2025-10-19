package spring.cource.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.cource.library.domain.Author;
import spring.cource.library.domain.Book;
import spring.cource.library.domain.Genre;
import spring.cource.library.dto.BookDto;
import spring.cource.library.exception.CouldNotSaveException;
import spring.cource.library.repository.AuthorRepository;
import spring.cource.library.repository.BookRepository;
import spring.cource.library.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@DisplayName("Сервис для работы с книгами")
@ExtendWith(MockitoExtension.class)
class BookServiceRestTest {
    @InjectMocks
    private BookServiceRest sut;
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
        doReturn(List.of(new Book().setId(25).setAuthor(new Author()).setGenre(new Genre()),
                new Book().setId(26).setAuthor(new Author()).setGenre(new Genre()))).when(bookRepository).findAll();
        var expected = sut.getAll();
        assertEquals(expected.size(), 2);
    }

    @Test
    @DisplayName("Должен вернуть книгу по id")
    void shouldGetBookById() {
        Author expectedAuthor = new Author().setId(1L).setName("author_name");
        Genre expectedGenre = new Genre().setId(11L);
        Book domainBook = new Book().setId(EXPECTED_ID).setName("name").setAuthor(expectedAuthor).setGenre(expectedGenre);
        BookDto expectedBook = BookDto.toDto(domainBook);
        doReturn(Optional.of(domainBook)).when(bookRepository).findById(EXPECTED_ID);

        BookDto result = sut.getById(EXPECTED_ID);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("Должен добавить книгу")
    void shouldAddNewBookSucessfully() {
        Book domainBook = generateDomainBook();
        BookDto expectedBook = BookDto.toDto(domainBook);
        doReturn(domainBook).when(bookRepository).save(any());

        BookDto result = sut.insertBook(expectedBook);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("Должен не смочь добавить книгу")
    void shouldNotAddNewBook() {
        //   Author author = new Author().setId(1L);
        //   Genre genre = new Genre().setId(11);
        BookDto bookDto = BookDto.toDto(generateDomainBook());
        doReturn(Optional.empty()).when(bookRepository).save(any());
        //  doReturn(Optional.of(author)).when(authorRepository).findById(1L);
        //  doReturn(Optional.of(genre)).when(genreRepository).findById(11L);
        var expectedException = new CouldNotSaveException("Не удалось добавить книгу в библиотеку");

        //when
        var thrown = assertThrows(CouldNotSaveException.class, () -> sut.insertBook(bookDto));
        assertThat(thrown).usingRecursiveComparison().isEqualTo(expectedException);

    }

    @Test
    @DisplayName("Должен обновить книгу")
    void shouldUpdateBookSucessfully() {
        Book domainBook = generateDomainBook();
        BookDto expectedBook = BookDto.toDto(domainBook);
        doReturn(Optional.of(new Book().setId(EXPECTED_ID))).when(bookRepository).findById(EXPECTED_ID);
        doReturn(domainBook).when(bookRepository).save(any());
        doReturn(Optional.of(new Author().setId(1L))).when(authorRepository).findById(1L);
        doReturn(Optional.of(new Genre().setId(11))).when(genreRepository).findById(11L);

        BookDto result = sut.updateBook(EXPECTED_ID, expectedBook);

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedBook);
        //   assertEquals(result, "Данные о книге c id = 25 обновлены");

    }

    private Book generateDomainBook() {
        Author author = new Author().setId(1L);
        Genre genre = new Genre().setId(11);
        return new Book().setId(EXPECTED_ID).setAuthor(author).setGenre(genre).setYear(1777);
    }
}