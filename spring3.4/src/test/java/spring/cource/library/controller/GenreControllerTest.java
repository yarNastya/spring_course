package spring.cource.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import spring.cource.library.dto.GenreDto;
import spring.cource.library.service.GenreService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Контроллер для работы с жанрами")
class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private GenreService genreService;

    @Test
    @WithMockUser("user")
    @DisplayName("должен добавлять жанр")
    void shouldAddNewGenre() throws Exception {
        GenreDto genreDto = new GenreDto(100, "name");
        String expectedResult = mapper.writeValueAsString(genreDto);

        mvc.perform(post("/genres")
                        .param("name", "name")
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResult));
    }
}