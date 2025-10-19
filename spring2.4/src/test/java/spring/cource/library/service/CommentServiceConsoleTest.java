package spring.cource.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.cource.library.dto.Book;
import spring.cource.library.dto.Comment;
import spring.cource.library.repository.BookRepository;
import spring.cource.library.repository.CommentRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@DisplayName("Сервис для работы с комментариями")
@ExtendWith(MockitoExtension.class)
class CommentServiceConsoleTest {

    @InjectMocks
    private CommentServiceConsole sut;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private BookRepository bookRepository;

    private final long EXPECTED_ID = 1L;

    @Test
    @DisplayName("Должен добавить комментарий")
    void shouldInsertComment() {
        doReturn(new Comment().setId(EXPECTED_ID)).when(commentRepository).save(any());
        doReturn(Optional.of(new Book().setId(1L))).when(bookRepository).findById(1L);
        String result = sut.insertComment("Test", 1L);
        assertEquals(result, "Добавлен комментарий с id = 1");
    }

    @Test
    @DisplayName("Должен удалить комментарий")
    void shouldDeleteComment() {
        doNothing().when(commentRepository).deleteById(EXPECTED_ID);
        String result = sut.deleteComment(EXPECTED_ID);
        assertEquals(result, "Комментарий удален");
    }

    @Test
    @DisplayName("Должен обновить комментарий")
    void updateComment() {
        doReturn(Optional.of(new Comment())).when(commentRepository).findById(EXPECTED_ID);
        doReturn(new Comment().setId(EXPECTED_ID)).when(commentRepository).save(any());
        String result = sut.updateComment(EXPECTED_ID, "newText");
        assertEquals(result, "Текст комментарий c id = 1 обновлен");
    }
}