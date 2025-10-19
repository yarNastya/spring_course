package spring.cource.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import spring.cource.library.dto.AuthorDto;
import spring.cource.library.service.AuthorService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Контроллер для работы с авторами")
class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private AuthorService authorService;

    @Test
    @DisplayName("Должен добавлять автора")
    void shouldAddNewAuthor() throws Exception {
        AuthorDto authorDto = new AuthorDto(100, "name", "patronymic", "surname");
        String expectedResult = mapper.writeValueAsString(authorDto);

        mvc.perform(post("/authors")
                        .param("name", "name")
                        .param("patronymic", "patronymic")
                        .param("surname", "surname")
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResult));

    }
}