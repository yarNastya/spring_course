package spring.cource.library.repository;

import spring.cource.library.dto.Genre;

import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> getById(long id);

    Optional<Long> insert(Genre genre);

}
