package spring.cource.library.dao;

import spring.cource.library.dto.Author;
import spring.cource.library.dto.Genre;

import java.util.Optional;

public interface GenreDao {
    Optional<Genre> getById(long id);

    Optional<Long> insert(Genre genre);

}
