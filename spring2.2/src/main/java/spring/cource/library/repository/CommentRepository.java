package spring.cource.library.repository;

import spring.cource.library.dto.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    Long insert(Comment comment);

    Long update(Comment comment);

    void deleteById(long id);

    Optional<Comment> getById(long id);

    List<Comment> getByBookId(long bookId);
}
