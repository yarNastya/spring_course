package spring.cource.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.cource.library.dao.AuthorDao;
import spring.cource.library.dao.BookDao;
import spring.cource.library.dao.GenreDao;
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
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public int count() {
        return bookDao.count();
    }

    @Override
    public List<Book> getAll() {
        return bookDao.getAllBooks();
    }

    @Override
    public String getById(Long id) {

        Optional<Book> optionalBook = bookDao.getById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            authorDao.getById(book.getAuthor().getId()).ifPresent(book::setAuthor);
            genreDao.getById(book.getGenre().getId()).ifPresent(book::setGenre);
            return String.format("Найдена книга: id:%d, название:%s, автор:%s, год:%d%n",
                    book.getId(), book.getName(), book.getAuthor().getName(), book.getYear());
        } else {
            return "Книга не найдена по id";
        }
    }

    @Override
    public String insertBook(String name, long authorId, long genreId, int year) {
        try {
            Optional<Author> author = authorDao.getById(authorId);
            if (author.isEmpty()) {
                return "Автор не найден по id";
            }
            Optional<Genre> genre = genreDao.getById(genreId);
            if (isNull(genre)) {
                return "Жанр не найден по id";
            }
            Book book = new Book().setName(name).setAuthor(author.get()).setGenre(genre.get()).setYear(year);

            return bookDao.insert(book)
                    .map(id -> String.format("Добавлена книга с id = %d", id))
                    .orElse("Не удалось добавить книгу в библиотеку");

        } catch (Exception ex) {
            return "Не удалось добавить книгу в библиотеку";
        }
    }

    @Override
    public String updateBook(long id, String name, Long authorId, Long genreId, Integer year) {
        try{
            Optional<Book> optionalBook = bookDao.getById(id);
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
                Optional<Author> optionalAuthor = authorDao.getById(authorId);
                if (optionalAuthor.isEmpty()) {
                    return "Автор не найден по id";
                } else {
                    book.setAuthor(optionalAuthor.get());
                }
            }
            if (nonNull(genreId)) {
                Optional<Genre> optionalGenre = genreDao.getById(genreId);
                if (isNull(optionalGenre)) {
                    return "Жанр не найден по id";
                } else {
                    book.setGenre(optionalGenre.get());
                }
            }

            return bookDao.update(book).map(bookId -> String.format("Данные о книге c id = %d обновлены", bookId))
                    .orElse("Не удалось обновить данные книги");

        } catch (Exception ex) {
            return "Не удалось обновить данные книги";
        }
    }

    public String deleteBook(long id) {

        try {
            Optional<Book> book = bookDao.getById(id);
            if (book.isEmpty()) {
                return "Книга не найдена по id";
            }
            bookDao.deleteById(id);
            return "Книга успешно удалена";
        } catch (Exception ex) {
            return "Не удалось удалить книгу";
        }
    }
}
