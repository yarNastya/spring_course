package spring.cource.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.cource.library.dto.Author;
import spring.cource.library.dto.Book;
import spring.cource.library.dto.Genre;
import spring.cource.library.repository.AuthorRepository;
import spring.cource.library.repository.BookRepository;
import spring.cource.library.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

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
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public String getById(Long id) {

        Optional<Book> optionalBook = bookRepository.findById(id);
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
            Optional<Author> author = authorRepository.findById(authorId);
            if (author.isEmpty()) {
                return "Автор не найден по id";
            }
            Optional<Genre> genre = genreRepository.findById(genreId);
            if (genre.isEmpty()) {
                return "Жанр не найден по id";
            }
            Book book = bookRepository.save(new Book().setName(name).setAuthor(author.get()).setGenre(genre.get()).setYear(year));
            return String.format("Добавлена книга с id = %d", book.getId());

        } catch (Exception ex) {
            return "Не удалось добавить книгу в библиотеку";
        }
    }

    @Override
    @Transactional
    public String updateBook(long id, String name, Long authorId, Long genreId, Integer year) {
        try {
            Optional<Book> optionalBook = bookRepository.findById(id);
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
                Optional<Author> optionalAuthor = authorRepository.findById(authorId);
                if (optionalAuthor.isEmpty()) {
                    return "Автор не найден по id";
                } else {
                    book.setAuthor(optionalAuthor.get());
                }
            }
            if (nonNull(genreId)) {
                Optional<Genre> optionalGenre = genreRepository.findById(genreId);
                if (optionalGenre.isEmpty()) {
                    return "Жанр не найден по id";
                } else {
                    book.setGenre(optionalGenre.get());
                }
            }
            bookRepository.save(book);
            return String.format("Данные о книге c id = %d обновлены", book.getId());

        } catch (Exception ex) {
            return "Не удалось обновить данные книги";
        }
    }

    @Override
    @Transactional
    public String deleteBook(long id) {
        try {
            Optional<Book> book = bookRepository.findById(id);
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
