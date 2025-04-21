package spring.cource.library.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import spring.cource.library.dto.Comment;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Long insert(Comment comment) {
        em.persist(comment);
        em.flush();
        return comment.getId();
    }

    @Override
    public Long update(Comment comment) {
        em.merge(comment);
        return comment.getId();
    }

    @Override
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Comment c where c.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Optional<Comment> getById(long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    @Override
    public List<Comment> getByBookId(long bookId) {
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.book.id=:bookId", Comment.class);
        query.setParameter("bookId", bookId);
        return query.getResultList();
    }
}
