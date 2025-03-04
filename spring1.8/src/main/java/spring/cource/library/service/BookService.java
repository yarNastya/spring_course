package spring.cource.library.service;

import spring.cource.library.dto.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {

    int count();

    List<Book> getAll();

    String getById(Long id);

    String insertBook(String name, long authorId, long genreId, int year);

    String updateBook(long id,String name, Long authorId, Long genreId, Integer year);

    String deleteBook(long id);

}


