package spring.cource.library.dao;

import spring.cource.library.dto.Book;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.List;
import java.util.Optional;

public interface BookDao {

    int count();
    Optional<Long> insert(Book book);

    Optional<Long> update(Book book);
    void deleteById(long id);

    Optional<Book> getById(long id);
    List<Book> getAllBooks();
}
