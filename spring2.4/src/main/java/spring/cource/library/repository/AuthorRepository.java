package spring.cource.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.cource.library.dto.Author;

public interface AuthorRepository extends JpaRepository<Author,Long> {


}
