package spring.cource.library.repository;

import spring.cource.library.dto.Author;

import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> getById(long id);
    Optional<Long> insert(Author author);
}
