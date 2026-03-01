package spring.cource.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import spring.cource.library.domain.Book;
import spring.cource.library.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Репозиторий для работы с комментариями")
class CommentRepositoryTest {

    @Autowired
    private CommentRepository sut;
    @Autowired
    private TestEntityManager em;

    private final long EXISTED_ID = 1L;

    @Test
    @DisplayName("Должен добавить комментарий")
    void shouldInsertComment() {
        Book book = em.find(Book.class,1L);
        Comment expectedComment = new Comment().setText("Text").setBook(book);
        sut.save(expectedComment);
        Comment actualComment = em.find(Comment.class,expectedComment.getId());
        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("Должен обновить комментарий")
    void shouldUpdateComment() {
        String text = "Test";
        Comment comment = em.find(Comment.class,EXISTED_ID);

        assertNotEquals(comment.getText(), text);

        comment.setText(text);
        sut.save(comment);
        em.flush();

        Comment actualComment = em.find(Comment.class,EXISTED_ID);
        assertEquals(actualComment.getText(), text);
    }

    @Test
    @DisplayName("Должен удалить комментарий")
    void shouldDeleteComment() {
        String text = "Test";
        Comment comment = em.find(Comment.class,EXISTED_ID);

        assertThat(comment).isNotNull();

        comment.setText(text);
        sut.deleteById(EXISTED_ID);
        sut.flush();

        Comment actualComment = em.find(Comment.class,EXISTED_ID);
        assertThat(actualComment).isNull();
    }

    @Test
    @DisplayName("Должен получить комментарий по ид")
    void shouldGetById() {
        Comment expectedComment = em.find(Comment.class, EXISTED_ID);

        Comment actualComment = sut.findById(EXISTED_ID).get();

        assertThat(actualComment).usingRecursiveComparison().isEqualTo(expectedComment);
    }
    @Test
    @DisplayName("Должен получить все комментарии по книге")
    void shouldGetByBookId() {
        List<Comment> actualList = sut.findByBookId(1L);
        assertThat(actualList).hasSize(1);
    }
}