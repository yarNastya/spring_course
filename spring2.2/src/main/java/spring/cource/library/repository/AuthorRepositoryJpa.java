package spring.cource.library.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spring.cource.library.dto.Author;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpa implements AuthorRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Author> getById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Optional<Long> insert(Author author) {
        em.persist(author);
        em.flush();
        return Optional.of(author.getId());
    }
}
