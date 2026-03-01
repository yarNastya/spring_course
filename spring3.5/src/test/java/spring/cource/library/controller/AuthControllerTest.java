package spring.cource.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import spring.cource.library.dto.AuthLoginRequest;
import spring.cource.library.service.JwtService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("Контроллер аутентификации")
class AuthControllerTest {

    @Autowired
    private org.springframework.test.web.servlet.MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private JwtService jwtService;

    @Test
    @DisplayName("должен вернуть токен при корректном логине")
    void shouldReturnTokenOnSuccessfulLogin() throws Exception {
        AuthLoginRequest request = new AuthLoginRequest("user", "pass");
        String expectedToken = "jwt-token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(new UsernamePasswordAuthenticationToken("user", null));
        when(jwtService.generateToken("user")).thenReturn(expectedToken);

        mvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"" + expectedToken + "\"}"));
    }

    @Test
    @DisplayName("должен вернуть 401")
    void shouldReturnUnauthorizedOnBadCredentials() throws Exception {
        AuthLoginRequest request = new AuthLoginRequest("user", "wrong");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        mvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}
