package spring.cource.library.service;

import spring.cource.library.domain.Book;
import spring.cource.library.dto.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {

    long count();

    List<BookDto> getAll();

    BookDto getById(Long id);

    BookDto insertBook(String name, long authorId, long genreId, int year);

    BookDto updateBook(long id, String name, Long authorId, Long genreId, Integer year);

    boolean deleteBook(long id);

    BookDto insertBook(BookDto book);

    BookDto updateBook(long id,BookDto book);

}


