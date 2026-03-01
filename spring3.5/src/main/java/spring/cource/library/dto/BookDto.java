package spring.cource.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import spring.cource.library.domain.Book;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BookDto {

    private long id;
    private String name;
    private AuthorDto author;
    private GenreDto genre;

    private int year;

    public static Book toDomainObject(BookDto bookDto) {
        return new Book(bookDto.getId(), bookDto.getName(), AuthorDto.toDomainObject(bookDto.getAuthor()),
                GenreDto.toDomainObject(bookDto.getGenre()), bookDto.getYear(), List.of());
    }

    public static BookDto toDto(Book book) {
        return new BookDto(book.getId(), book.getName(), AuthorDto.toDto(book.getAuthor()),
                GenreDto.toDto(book.getGenre()), book.getYear());
    }

    public static BookDto toDtoSimple(Book book) {
        return new BookDto(book.getId(), book.getName(), null,
                null, book.getYear());
    }
}
