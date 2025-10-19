package spring.cource.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import spring.cource.library.domain.Book;
import spring.cource.library.domain.Comment;
import spring.cource.library.dto.CommentDto;
import spring.cource.library.repository.BookRepository;
import spring.cource.library.repository.CommentRepository;
import spring.cource.library.service.CommentService;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Контроллер для работы с комментариями")
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CommentService commentService;
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;
    private static final Long EXISTING_COMMENT_ID = 1L;
    private static final Long EXISTING_BOOK_ID = 1L;

    @Test
    @DisplayName("Должен добавлять комментарий к книге")
    void shouldAddNewComment() throws Exception {
        Optional<Book> book = bookRepository.findById(EXISTING_BOOK_ID);
        Comment comment = new Comment(100, "text", book.get());
        CommentDto commentDto = CommentDto.toDto(comment);

        String expectedResult = mapper.writeValueAsString(commentDto);

        mvc.perform(post("/books/comments")
                        .param("text", "text")
                        .param("bookId", EXISTING_BOOK_ID.toString())
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResult));

    }

    @Test
    @DisplayName("должен обновлять комментарий к книге")
    void shouldUpdateComment() throws Exception {
        String newText = "newText";
        Optional<Comment> comment = commentRepository.findById(EXISTING_COMMENT_ID);
        CommentDto commentDto = CommentDto.toDto(comment.get().setText(newText));
        String expectedResult = mapper.writeValueAsString(commentDto);
        mvc.perform(put("/books/1/comments/1")
                        .param("text", newText)

                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @Test
    void shouldGetAllCommentsByBookId() throws Exception {
        List<CommentDto> commentList = commentService.getCommentsByBookId(EXISTING_BOOK_ID);
        String expectedResult = mapper.writeValueAsString(commentList);
        mvc.perform(get("/books/1/comments")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }
}