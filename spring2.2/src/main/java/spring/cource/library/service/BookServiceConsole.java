package spring.cource.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.cource.library.repository.AuthorRepository;
import spring.cource.library.repository.BookRepository;
import spring.cource.library.repository.GenreRepository;
import spring.cource.library.dto.Author;
import spring.cource.library.dto.Book;
import spring.cource.library.dto.Genre;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class BookServiceConsole implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return bookRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAll() {
        return bookRepository.getAllBooks();
    }

    @Override
    @Transactional(readOnly = true)
    public String getById(Long id) {

        Optional<Book> optionalBook = bookRepository.getById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            return String.format("Найдена книга: id:%d, название:%s, автор:%s, год:%d%n",
                    book.getId(), book.getName(), book.getAuthor().getName(), book.getYear());
        } else {
            return "Книга не найдена по id";
        }
    }

    @Override
    @Transactional
    public String insertBook(String name, long authorId, long genreId, int year) {
        try {
            Optional<Author> author = authorRepository.getById(authorId);
            if (author.isEmpty()) {
                return "Автор не найден по id";
            }
            Optional<Genre> genre = genreRepository.getById(genreId);
            if (isNull(genre)) {
                return "Жанр не найден по id";
            }
            Book book = new Book().setName(name).setAuthor(author.get()).setGenre(genre.get()).setYear(year);

            return bookRepository.insert(book)
                    .map(id -> String.format("Добавлена книга с id = %d", id))
                    .orElse("Не удалось добавить книгу в библиотеку");

        } catch (Exception ex) {
            return "Не удалось добавить книгу в библиотеку";
        }
    }

    @Override
    @Transactional
    public String updateBook(long id, String name, Long authorId, Long genreId, Integer year) {
        try{
            Optional<Book> optionalBook = bookRepository.getById(id);
            if (optionalBook.isEmpty()) {
                return "Не удалось найти книгу по id";
            }
            Book book = optionalBook.get();
            if (nonNull(name)) {
                book.setName(name);
            }
            if (nonNull(year)) {
                book.setYear(year);
            }
            if (nonNull(authorId)) {
                Optional<Author> optionalAuthor = authorRepository.getById(authorId);
                if (optionalAuthor.isEmpty()) {
                    return "Автор не найден по id";
                } else {
                    book.setAuthor(optionalAuthor.get());
                }
            }
            if (nonNull(genreId)) {
                Optional<Genre> optionalGenre = genreRepository.getById(genreId);
                if (isNull(optionalGenre)) {
                    return "Жанр не найден по id";
                } else {
                    book.setGenre(optionalGenre.get());
                }
            }

            return bookRepository.update(book).map(bookId -> String.format("Данные о книге c id = %d обновлены", bookId))
                    .orElse("Не удалось обновить данные книги");

        } catch (Exception ex) {
            return "Не удалось обновить данные книги";
        }
    }

    @Override
    @Transactional
    public String deleteBook(long id) {

        try {
            Optional<Book> book = bookRepository.getById(id);
            if (book.isEmpty()) {
                return "Книга не найдена по id";
            }
            bookRepository.deleteById(id);
            return "Книга успешно удалена";
        } catch (Exception ex) {
            return "Не удалось удалить книгу";
        }
    }
}
