package spring.cource.library.repository;

import spring.cource.library.dto.Book;
import spring.cource.library.dto.Comment;

import java.util.List;
import java.util.Optional;

public interface BookRepository {
    long count();
    Optional<Long> insert(Book book);
    Optional<Long> update(Book book);
    void deleteById(long id);
    Optional<Book> getById(long id);
    List<Book> getAllBooks();
}
