package spring.cource.library.dao;

import spring.cource.library.dto.Author;
import spring.cource.library.dto.Book;

import java.util.Optional;

public interface AuthorDao {

    Optional<Author> getById(long id);

    Optional<Long> insert(Author author);
}
