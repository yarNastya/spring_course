package spring.cource.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.cource.library.domain.Genre;

public interface GenreRepository extends JpaRepository<Genre,Long> {
}
