package spring.cource.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Контроллер для работы с аутентификацией")
class PageControllerTest {

    @Autowired
    private MockMvc mvc;

    @WithMockUser(username = "user")
    @Test
    @DisplayName("должен вернуть страницу по умолчанию")
    void shouldShowIndexPage() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("должен вернуть страницу успеха")
    void shouldShowSuccessPage() throws Exception {
        mvc.perform(get("/success"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user")
    @Test
    @DisplayName("должен вернуть страницу неуспеха")
    void shouldShowErrorPage() throws Exception {
        mvc.perform(get("/success"))
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("должен перенаправить на аутентификацию")
    void shouldReturnRedirect() throws Exception {
        mvc.perform(get("/success"))
                .andExpect(status().is3xxRedirection());
    }
}