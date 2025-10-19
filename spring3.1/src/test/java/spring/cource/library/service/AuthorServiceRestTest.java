package spring.cource.library.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.cource.library.domain.Author;
import spring.cource.library.dto.AuthorDto;
import spring.cource.library.repository.AuthorRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@DisplayName("Сервис для работы с авторами")
@ExtendWith(MockitoExtension.class)
class AuthorServiceRestTest {

    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorServiceRest sut;

    @Test
    @DisplayName("Должен добавить автора")
    void insertAuthor() {
        Author domainAuthor = new Author().setId(100L).setName("name").setPatronymic("pat").setSurname("sur");
        AuthorDto expectedAuthor = AuthorDto.toDto(domainAuthor);
        doReturn(domainAuthor).when(authorRepository).save(any());

        AuthorDto result = sut.insertAuthor(expectedAuthor.getName(), expectedAuthor.getPatronymic(), expectedAuthor.getSurname());

        assertThat(result).usingRecursiveComparison().isEqualTo(expectedAuthor);
    }
}