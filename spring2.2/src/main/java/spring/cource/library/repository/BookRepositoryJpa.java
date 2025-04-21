package spring.cource.library.repository;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spring.cource.library.dto.Book;
import spring.cource.library.dto.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public long count() {
        TypedQuery<Long> query = em.createQuery("select count(b) from Book b", Long.class);
        return query.getSingleResult();
    }

    @Override
    public Optional<Long> insert(Book book) {
        try {
            em.persist(book);
            em.flush();
            return Optional.of(book.getId());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> update(Book book) {
        em.merge(book);
        return Optional.of(book.getId());
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Book> getById(long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> getAllBooks() {
        EntityGraph<?> graph = em.getEntityGraph("book-graph");
        TypedQuery<Book> query = em.createQuery("select b from Book b", Book.class);
        query.setHint("javax.persistence.fetchgraph",graph);
        return query.getResultList();
    }

}
