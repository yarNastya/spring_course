package spring.cource.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import spring.cource.library.domain.Author;
import spring.cource.library.domain.Genre;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class GenreDto {

    private long id;

    private String name;

    public static Genre toDomainObject(GenreDto genreDto) {
        return new Genre(genreDto.getId(), genreDto.getName());
    }

    public static GenreDto toDto(Genre genre) {
        return new GenreDto(genre.getId(), genre.getName());
    }
}
