package spring.cource.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import spring.cource.library.domain.Author;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class AuthorDto {
    private long id;

    private String name;

    private String patronymic;

    private String surname;

    public static Author toDomainObject(AuthorDto authorDto) {
        return new Author(authorDto.getId(), authorDto.getName(), authorDto.getPatronymic(), authorDto.getSurname());
    }

    public static AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getName(), author.getPatronymic(), author.getSurname());
    }
}
