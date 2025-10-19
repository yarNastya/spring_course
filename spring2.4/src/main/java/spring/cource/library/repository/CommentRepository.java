package spring.cource.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.cource.library.dto.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBookId(Long bookId);
}
