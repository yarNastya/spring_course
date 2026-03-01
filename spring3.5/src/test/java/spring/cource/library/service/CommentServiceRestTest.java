package spring.cource.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.cource.library.domain.Book;
import spring.cource.library.domain.Comment;
import spring.cource.library.dto.CommentDto;
import spring.cource.library.repository.BookRepository;
import spring.cource.library.repository.CommentRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@DisplayName("Сервис для работы с комментариями")
@ExtendWith(MockitoExtension.class)
class CommentServiceRestTest {

    @InjectMocks
    private CommentServiceRest sut;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookRepository bookRepository;

    private final long EXPECTED_ID = 1L;


    @Test
    @DisplayName("Должен добавить комментарий")
    void shouldInsertComment() {
        Book domainBook = new Book().setId(1L);
        Comment domainComment = new Comment().setId(EXPECTED_ID).setText("Test").setBook(domainBook);
        CommentDto expectedComment = CommentDto.toDto(domainComment);
        doReturn(domainComment).when(commentRepository).save(any());
        doReturn(Optional.of(domainBook)).when(bookRepository).findById(1L);

        CommentDto result = sut.insertComment(expectedComment.getText(), expectedComment.getId());

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("Должен удалить комментарий")
    void shouldDeleteComment() {
        doNothing().when(commentRepository).deleteById(EXPECTED_ID);
        boolean result = sut.deleteComment(EXPECTED_ID);
        assertTrue(result);

    }

    @Test
    @DisplayName("Должен обновить комментарий")
    void updateComment() {
        Comment domainComment = new Comment().setId(EXPECTED_ID).setText("newText").setBook(new Book().setId(1L));
        CommentDto expectedComment = CommentDto.toDto(domainComment);
        doReturn(Optional.of(new Comment())).when(commentRepository).findById(EXPECTED_ID);
        doReturn(domainComment).when(commentRepository).save(any());

        CommentDto result = sut.updateComment(expectedComment.getId(), expectedComment.getText());

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedComment);
    }
}