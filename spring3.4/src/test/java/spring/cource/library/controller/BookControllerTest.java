package spring.cource.library.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import spring.cource.library.domain.Author;
import spring.cource.library.domain.Book;
import spring.cource.library.dto.AuthorDto;
import spring.cource.library.dto.BookDto;
import spring.cource.library.dto.GenreDto;
import spring.cource.library.repository.AuthorRepository;
import spring.cource.library.repository.BookRepository;
import spring.cource.library.service.BookService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@DisplayName("Контроллер для работы с книгами")
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorRepository authorRepository;

    private static final Long EXISTING_BOOK_ID = 1L;


    @Test
    @DisplayName("должен получить код перенаправления")
    void shouldGetRedirect() throws Exception {
        mvc.perform(get("/books"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен получать список книг")
    void shouldGetAllBooks() throws Exception {
        List<BookDto> bookDtoList = bookService.getAll();

        String expectedResult = mapper.writeValueAsString(bookDtoList);

        mvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен считать количество книг")
    void shouldGetBookCount() throws Exception {
        Long count = bookService.count();

        String expectedResult = mapper.writeValueAsString(count);

        mvc.perform(get("/books/count"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен получить книгу по ИД")
    void shouldGetBookById() throws Exception {
        BookDto bookDto = bookService.getById(EXISTING_BOOK_ID);

        String expectedResult = mapper.writeValueAsString(bookDto);

        mvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("не должен получить книгу по ИД")
    void shouldNotGetBookById() throws Exception {

        mvc.perform(get("/books/1000"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Книга не найдена по ИД"));
    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен добавлять книгу")
    void shouldAddNewBook() throws Exception {
        BookDto bookDto = new BookDto()
                .setName("Test")
                .setAuthor(new AuthorDto().setId(1L))
                .setGenre(new GenreDto().setId(1L))
                .setYear(1999);

        mvc.perform(post("/books").contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(bookDto.setId(100L))));
    }

    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен обновлять книгу")
    void shouldUpdateBook() throws Exception {

        BookDto bookDto = bookService.getById(EXISTING_BOOK_ID);

        Author author = authorRepository.findById(2L).get();
        bookDto.setAuthor(AuthorDto.toDto(author));

        mvc.perform(put("/books/1")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookDto)));
    }


    @Test
    @WithMockUser(username = "user")
    @DisplayName("должен удалять книгу")
    void shouldDeleteBook() throws Exception {
        Optional<Book> book = bookRepository.findById(EXISTING_BOOK_ID);
        assertTrue(book.isPresent());

        mvc.perform(delete("/books/1"))
                .andExpect(status().is2xxSuccessful());
        book = bookRepository.findById(EXISTING_BOOK_ID);
        //     assertTrue(book1.isPresent());
        assertFalse(book.isPresent());
    }


}